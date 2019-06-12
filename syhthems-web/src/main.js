import Vue from 'vue'
import './plugins/vuetify'
import App from './App.vue'
import router from './router'
import store from './store'
import SyhthemsOAuth2 from './plugins/SyhthemsOAuth2'
import config from './config'
import './plugins/axios'
import './plugins/vuetify-toast-snackbar'
import './plugins/vuetify-dialog'
import VeLine from 'v-charts/lib/line.common'

Vue.component(VeLine.name, VeLine)

Vue.config.productionTip = false

Vue.use(SyhthemsOAuth2, {
  ...config.oauth2,
  store
})

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')
