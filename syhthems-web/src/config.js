const config = {
  apiUrl: 'http://localhost:8082/web/api',
  ssoUrl: 'http://localhost:8081/sso'
}

config.oauth2 = {
  authorizationServer: config.ssoUrl,
  resourceServer: config.apiUrl,
  clientId: 'syhthems-web',
  accessTokenUrl: `${config.ssoUrl}/oauth/token`,
  userAuthorizationUrl: `${config.ssoUrl}/oauth/authorize`,
  tokenInfoUrl: `${config.ssoUrl}/oauth/check_token`,
  tokenName: 'token',
  storage: 'localStorage',
  tokenType: 'Bearer'
}

export default config
