const config = {
  apiUrl: process.env.SYHTHEMS_API_URL || 'http://gateway.syhthems.local:8080/api/web/api',
  ssoUrl: process.env.SYHTHEMS_SSO_URL || 'http://gateway.syhthems.local:8080/sso'
}

config.oauth2 = {
  authorizationServer: config.ssoUrl,
  resourceServer: config.apiUrl,
  clientId: 'syhthems-web',
  clientSecret: 'sunriseydy-syhthems-web-secret',
  accessTokenUrl: `${config.ssoUrl}/oauth/token`,
  userAuthorizationUrl: `${config.ssoUrl}/oauth/authorize`,
  tokenInfoUrl: `${config.ssoUrl}/oauth/check_token`,
  tokenName: 'token',
  storage: 'localStorage',
  tokenType: 'Bearer'
}

export default config
