<template>
  <!-- todo -->
  <v-app
    id="inspire"
    :dark="darkTheme"
  >
    <router-view name="menu" />
    <router-view name="toolbar" />
    <v-content>
      <v-container
        fluid
      >
        <v-fade-transition mode="out-in">
          <router-view />
        </v-fade-transition>
      </v-container>
    </v-content>
    <v-footer
      height="auto"
      app
      inset
    >
      <v-row
        justify="center"
      >
        <v-col
          class="text-md-center pa-2"
          cols="12"
        >
          &copy; {{ new Date().getFullYear() }} — <strong>Sunrise YDY</strong>
        </v-col>
      </v-row>
    </v-footer>
    <syhthems-loading />
  </v-app>
</template>

<script>
  import SyhthemsLoading from './components/SyhthemsLoading'
  const _ = require('lodash/core')

  export default {
    name: 'APP',
    components: {
      SyhthemsLoading,
    },
    data () {
      return {
      //
      }
    },
    computed: {
      darkTheme () {
        return this.$store.state.darkTheme
      },
    },
    created () {
      console.log('APP created...')
      if (window.localStorage.getItem('darkTheme')) {
        this.$store.commit('setDarkTheme', { darkTheme: (window.localStorage.getItem('darkTheme') === true.toString()), theme: this.$vuetify.theme })
      }
      // 注册全局导航守卫
      this.$router.beforeEach((to, from, next) => {
        console.log('router beforeEach...')
        if (to.name === 'login') {
          next()
        } else if (to.name === '404') {
          next()
        } else {
          if (this.$oauth2.isAuthenticated()) {
            next()
          } else if (!this.$oauth2.isRefreshTokenExpired()) {
            this.$dialog.notify.error(('token已过期，即将重新获取token'), {
              position: 'bottom-right',
              timeout: 3000,
            })
            this.$router.push({
              name: 'login',
              query: {
                refreshToken: true,
              },
            })
            next()
          } else {
            this.$dialog.notify.error(('token已过期，请重新登录'), {
              position: 'bottom-right',
              timeout: 3000,
            })
            this.$router.push({ name: 'login' })
            next()
          }
        }
      })

      // 注册全局的http返回拦截器
      // 全局异常处理
      // 若请求成功弹出提示
      this.$http.interceptors.response.use(response => {
                                             // Do something with response data
                                             if (response.status === 200 && !_.isEmpty(response.data) && response.data.code === 0) {
                                               this.$dialog.notify.success('请求成功', {
                                                 position: 'bottom-right',
                                                 timeout: 1000,
                                               })
                                             }
                                             return response
                                           },
                                           error => {
                                             // Do something with response error
                                             if (error.response) {
                                               // The request was made and the server responded with a status code
                                               // that falls out of the range of 2xx
                                               console.log('error.response.data: ')
                                               console.log(error.response.data)
                                               console.log('error.response.status: ')
                                               console.log(error.response.status)
                                               console.log('error.response.headers: ')
                                               console.log(error.response.headers)
                                               this.$dialog.notify.error(('Error: ' + error.response.status + ': ' + (error.response.data.message || '请求失败')), {
                                                 position: 'bottom-right',
                                                 timeout: 3000,
                                               })
                                               if (error.response.status === 401) {
                                                 this.$dialog.notify.error(('您的token已过期，请重新登录'), {
                                                   position: 'bottom-right',
                                                   timeout: 3000,
                                                 })
                                                 this.$router.push({ name: 'login' })
                                               }
                                             } else if (error.request) {
                                               // The request was made but no response was received
                                               // `error.request` is an instance of XMLHttpRequest in the browser and an instance of
                                               // http.ClientRequest in node.js
                                               this.$dialog.notify.error('Error: 无法连接服务器', {
                                                 position: 'bottom-right',
                                                 timeout: 3000,
                                               })
                                               console.log('error.request: ')
                                               console.log(error.request)
                                             } else {
                                               // Something happened in setting up the request that triggered an Error
                                               this.$dialog.notify.error('Error: ' + error.message, {
                                                 position: 'bottom-right',
                                                 timeout: 3000,
                                               })
                                               console.log('Error: ')
                                               console.log(error.message)
                                             }
                                             console.log('error.config: ')
                                             console.log(error.config)
                                             return Promise.reject(error)
                                           })

      if (this.$oauth2.isAuthenticated()) {
        if (this.$store.state.menu.rawMenus.length === 0) {
          this.fetchRoutes()
        }
      } else if (this.$route.name !== 'login') {
        if (!this.$oauth2.isRefreshTokenExpired()) {
          this.$dialog.notify.error('token已过期，正在刷新token', {
            position: 'bottom-right',
            timeout: 3000,
          })
          this.$router.push({
            name: 'login',
            query: {
              refreshToken: true,
            },
          })
        } else {
          this.$dialog.notify.error('请登录', {
            position: 'bottom-right',
            timeout: 3000,
          })
          this.$router.push('login')
        }
      }
    },
    mounted () {
      console.log('APP mounted...')
    },
    methods: {
      fetchRoutes () {
        this.$oauth2.doGetUserDetails().then(value => {
          this.$store.commit('setUserDetails', value)
          this.$store.commit('menu/setRawMenus', value.menus)
          const routes = this.$store.getters['menu/getRoutes']
          this.$router.addRoutes(routes)
        })
      },
    },
  }
</script>
