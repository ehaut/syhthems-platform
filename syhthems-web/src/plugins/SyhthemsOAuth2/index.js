import SyhthemsOAuth2 from './SyhthemsOAuth2'

export default {
  installed: false,
  install (Vue, options) {
    if (this.installed) {
      return
    }
    this.installed = true
    if (Vue.axios) {
      options.axios = Vue.axios
    }
    Vue.prototype.$oauth2 = new SyhthemsOAuth2(options)
  }
}
