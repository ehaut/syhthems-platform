import qs from 'qs'

export default class SyhthemsOAuth2 {
  constructor (options) {
    this.options = options
    this.storage = window.localStorage
    this.tokenName = options.tokenName || 'token'
    this.refreshTokenName = options.refreshTokenName || 'refreshToken'
    if (options.store) {
      this.$_store = options.store
      this.registerVuexModule()
    }
    if (options.axios) {
      this.$_http = options.axios
      if (this.isAuthenticated()) {
        this.requestInterceptor = this.registerRequestInterceptor()
      } else {
        this.requestInterceptor = 0
      }
    }
  }

  registerVuexModule () {
    if (this.$_store) {
      this.$_store.registerModule('oauth2', {
        namespaced: true,
        state: {
          token: this.getToken(),
          refreshToken: this.getRefreshToken()
        },
        getters: {
          isAuthenticated (state) {
            let token = state.token
            return SyhthemsOAuth2._isAuthenticated(token)
          },

          isExpired (state) {
            let token = state.token
            return SyhthemsOAuth2._isExpired(token)
          }
        },
        mutations: {
          setToken (state, token) {
            state.token = token
          },

          setRefreshToken (state, refreshToken) {
            state.refreshToken = refreshToken
          }
        }
      })
    }
  }

  /**
   * 注册request拦截器，为request请求添加token认证header
   * 一定要在获取token后再注册
   * @returns {number} 注册的拦截器id
   */
  registerRequestInterceptor () {
    if (this.$_http) {
      return this.$_http.interceptors.request.use((requestConfig) => {
        if (requestConfig.baseURL && requestConfig.baseURL === this.options.resourceServer && requestConfig.url !== '/oauth/token') {
          if (this.isExpired() && !this.isRefreshTokenExpired()) {
            this.doRefreshTokenRequest().then(value => {
              if (requestConfig.headers) {
                requestConfig.headers['Authorization'] = 'Bearer ' + this.getToken()
              } else {
                requestConfig.headers = {
                  'Authorization': 'Bearer ' + this.getToken()
                }
              }
              return requestConfig
            })
          }
          if (requestConfig.headers) {
            requestConfig.headers['Authorization'] = 'Bearer ' + this.getToken()
          } else {
            requestConfig.headers = {
              'Authorization': 'Bearer ' + this.getToken()
            }
          }
        }
        return requestConfig
      })
    }
  }

  static _isAuthenticated (token) {
    return !this._isExpired(token)
  }

  static _isExpired (token) {
    if (token) { // Token is present
      if (token.split('.').length === 3) { // Token with a valid JWT format XXX.YYY.ZZZ
        try { // Could be a valid JWT or an access token with the same format
          let exp = this.getTokenClaimsParameter(token, 'exp')
          if (exp && typeof exp === 'number') { // JWT with an optonal expiration claims
            return !(Math.round(new Date().getTime() / 1000) < exp)
          } else {
            return false
          }
        } catch (e) {
          return false // Pass: Non-JWT token that looks like JWT
        }
      }
      return false // Pass: All other tokens
    }
    return true // token doesn't exist
  }

  /**
   * 判断是否登录
   * 根据token信息来判断，如果token是JWT则判断是否过期，是其他类型的token就判断是否存在token
   * @returns {boolean}
   */
  isAuthenticated () {
    let token = this.getToken()
    return SyhthemsOAuth2._isAuthenticated(token)
  }

  /**
   * 判断token是否过期
   * @param token
   * @returns {boolean}
   */
  isExpired () {
    let token = this.getToken()
    return SyhthemsOAuth2._isExpired(token)
  }

  /**
   * 判断本地存储的RefreshToken是否过期
   * @returns {*|boolean}
   */
  isRefreshTokenExpired () {
    let refreshToken = this.getRefreshToken()
    return SyhthemsOAuth2._isExpired(refreshToken)
  }

  /**
   * Get token if user is authenticated
   * @return {String} Authentication token
   */
  getToken () {
    return this.storage.getItem(this.tokenName)
  }

  /**
   * Set new authentication token
   * @param {String|Object} token
   */
  setToken (token) {
    this.storage.setItem(this.tokenName, token)
    if (this.$_store) {
      this.$_store.commit('oauth2/setToken', token)
    }
    if (this.$_http) {
      if (this.requestInterceptor) {
        this.$_http.interceptors.request.eject(this.requestInterceptor)
      }
      this.requestInterceptor = this.registerRequestInterceptor()
    }
  }

  getRefreshToken () {
    return this.storage.getItem(this.refreshTokenName)
  }

  setRefreshToken (refreshToken) {
    this.storage.setItem(this.refreshTokenName, refreshToken)
    if (this.$_store) {
      this.$_store.commit('oauth2/setRefreshToken', refreshToken)
    }
  }

  setRandomState (state) {
    this.storage.setItem('randomState', state)
  }

  getRandomState () {
    return this.storage.getItem('randomState')
  }

  generateRandomState () {
    let state = Math.random().toString(36).slice(2, 12)
    this.setRandomState(state)
    return state
  }

  static decodeJWT (token) {
    if (token.split('.').length === 3) {
      try {
        let headers = token.split('.')[0].replace('-', '+').replace('_', '/')
        let payload = token.split('.')[1].replace('-', '+').replace('_', '/')
        let rawSignature = token.split('.')[2].replace('-', '+').replace('_', '/')
        let rawHeaders = JSON.parse(window.atob(headers))
        let rawClaims = JSON.parse(window.atob(payload))
        return [rawHeaders, rawClaims, rawSignature]
      } catch (e) {
        return false
      }
    } else {
      return false
    }
  }

  static getTokenClaimsParameter (token, parameterName) {
    if (SyhthemsOAuth2.decodeJWT(token)) {
      let claims = SyhthemsOAuth2.decodeJWT(token)[1]
      return claims[parameterName]
    } else {
      return false
    }
  }

  getLoginUsername () {
    let username = ''
    if (this.$_store.state.userDetails) {
      username = this.$_store.state.userDetails.username
    } else if (SyhthemsOAuth2.getTokenClaimsParameter('user_name')) {
      username = SyhthemsOAuth2.getTokenClaimsParameter('user_name')
    }
    return username
  }

  getLoginUrl () {
    let loginUrl = this.options.authorizationServer + '/oauth/authorize'
    loginUrl += qs.stringify({
      response_type: 'code',
      client_id: this.options.clientId,
      redirect_uri: window.location.origin + window.location.pathname,
      state: this.generateRandomState()
    }, { addQueryPrefix: true })
    return loginUrl
  }

  getLogoutUrl () {
    let logoutUrl = this.options.authorizationServer + '/logout'
    let username
    if (this.$_store.state.userDetails) {
      username = this.$_store.state.userDetails.username
    } else if (SyhthemsOAuth2.getTokenClaimsParameter('user_name')) {
      username = SyhthemsOAuth2.getTokenClaimsParameter('user_name')
    }
    logoutUrl += qs.stringify({
      username: username,
      redirect_uri: window.location.origin + window.location.pathname
    }, { addQueryPrefix: true })
    return logoutUrl
  }

  doLogout () {
    if (this.isAuthenticated()) {
      this.setToken('')
      this.setRefreshToken('')
      this.$_store.commit('setUserDetails', null)
      this.$_store.commit('setProduct', null)
      this.$_store.commit('setDevices', [])
      window.location.href = this.getLogoutUrl()
    }
  }

  /**
   * 获取code
   * @returns {AxiosPromise<any> | *}
   */
/*  doAuthorizationRequest () {
    let authorizationEndpoint = '/oauth/authorize'
    let url = authorizationEndpoint + qs.stringify({
      response_type: 'code',
      client_id: this.options.clientId,
      redirect_uri: window.location.origin + window.location.pathname,
      state: this.generateRandomState()
    }, { addQueryPrefix: true })
    let requestConfig = {
      headers: { 'content-type': 'application/x-www-form-urlencoded' },
      baseURL: this.options.authorizationServer
    }
    return this.$_http.get(url, requestConfig)
  } */

  /**
   * 获取token
   * @param queryObject
   * @returns {Promise<any>}
   */
  doAccessTokenRequest (queryObject) {
    return new Promise((resolve, reject) => {
      if (queryObject.code) {
        let code = ''
        if (queryObject.state) { // 返回结果带有state
          if (queryObject.state === this.getRandomState()) {
            code = queryObject.code
          } else {
            this.setRandomState('') // 清空本地的state
            reject(new Error('认证服务器返回的state不匹配'))
          }
        } else { // 返回结果没有state
          if (this.getRandomState()) { // 如果本地存在state
            this.setRandomState('') // 清空本地的state
            reject(new Error('认证服务器返回结果缺少state参数'))
          } else { // 本地也没有state，那么就可以获取token
            code = queryObject.code
          }
        }
        this.setRandomState('') // 清空本地的state
        if (code) {
          // 开始获取token
          let data = {
            grant_type: 'authorization_code',
            code: code,
            redirect_uri: window.location.origin + window.location.pathname,
            client_id: this.options.clientId
          }
          let requestConfig = {
            headers: { 'content-type': 'application/x-www-form-urlencoded' },
            baseURL: this.options.resourceServer
            /* auth: {
              username: this.options.clientId,
              password: this.options.clientSecret
            } */
          }
          this.$_http.post('/oauth/token', qs.stringify(data), requestConfig)
              .then((response) => {
                if (response.status === 200) {
                  if (response.data && response.data.access_token && response.data.refresh_token) {
                    this.setToken(response.data.access_token)
                    this.setRefreshToken(response.data.refresh_token)
                    resolve('登录成功')
                  }
                  reject(new Error('获取token参数失败'))
                }
                reject(new Error('token请求响应失败'))
              })
              .catch((error) => {
                reject(error)
              })
        }
      } else {
        reject(new Error('返回响应中缺少 code 参数'))
      }
    })
  }

  doGetUserDetails () {
    return new Promise((resolve, reject) => {
      if (this.isAuthenticated()) {
        this.$_http.get('/').then(res => {
          if (res.status === 200 && Object.keys(res.data) && res.data.code === 0) {
            resolve(res.data.data)
          }
        }).catch(reason => (reject(reason)))
      } else {
        reject(new Error('未认证'))
      }
    })
  }

  /**
   * 刷新Token
   * @returns {Promise<any>}
   */
  doRefreshTokenRequest () {
    return new Promise((resolve, reject) => {
      if (this.isExpired() && !this.isRefreshTokenExpired()) {
        let data = {
          grant_type: 'refresh_token',
          refresh_token: this.getRefreshToken()
        }
        let requestConfig = {
          headers: { 'content-type': 'application/x-www-form-urlencoded' },
          baseURL: this.options.resourceServer
          /* auth: {
            username: this.options.clientId,
            password: this.options.clientSecret
          } */
        }
        this.$_http.post('/oauth/token', qs.stringify(data), requestConfig)
            .then((response) => {
              if (response.status === 200) {
                if (response.data && response.data.access_token) {
                  this.setToken(response.data.access_token)
                  if (response.data.refresh_token) {
                    this.setRefreshToken(response.data.refresh_token)
                  }
                  resolve('刷新令牌成功')
                }
                reject(new Error('获取token参数失败'))
              }
              reject(new Error('刷新token请求失败'))
            })
            .catch((error) => {
              reject(error)
            })
      } else {
        reject(new Error('refresh token failed'))
      }
    })
  }
}
