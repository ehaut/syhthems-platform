import Vue from 'vue'
import Vuetify, { VIcon, VSnackbar, VAlert, VDialog, VLayout,
  VBtn, VCard, VCardActions, VCardText, VCardTitle, VSpacer } from 'vuetify/lib'
import 'vuetify/src/stylus/app.styl'
import zhHans from 'vuetify/es5/locale/zh-Hans'
import '@fortawesome/fontawesome-free/css/all.css'

const MY_ICONS = {
  'apps': 'fas fa-th',
  'add': 'fas fa-plus',
  'refresh': 'fas fa-sync-alt',
  'notifications': 'fas fa-bell',
  'home': 'fas fa-home',
  'stream': 'fas fa-stream',
  'device': 'fas fa-microchip',
  'down': 'fas fa-chevron-down',
  'up': 'fas fa-chevron-up'
}

Vue.use(Vuetify, {
  lang: {
    locales: zhHans,
    current: 'zhHans'
  },
  iconfont: 'fa',
  icons: MY_ICONS,
  components: {
    VIcon,
    VSnackbar,
    VAlert,
    VDialog,
    VLayout,
    VBtn,
    VCard,
    VCardTitle,
    VCardText,
    VCardActions,
    VSpacer
  }
})
