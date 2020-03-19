import Vue from 'vue'
import Vuetify, {
  VIcon, VSnackbar, VAlert, VDialog, VLayout,
  VBtn, VCard, VCardActions, VCardText, VCardTitle, VSpacer, VAppBar,
} from 'vuetify/lib'
import zhHans from 'vuetify/es5/locale/zh-Hans'
import en from 'vuetify/es5/locale/en'
import '@fortawesome/fontawesome-free/css/all.css'

const MY_ICONS = {
  apps: 'fas fa-th',
  add: 'fas fa-plus',
  refresh: 'fas fa-sync-alt',
  notifications: 'fas fa-bell',
  home: 'fas fa-home',
  stream: 'fas fa-stream',
  device: 'fas fa-microchip',
  down: 'fas fa-chevron-down',
  up: 'fas fa-chevron-up',
  search: 'fas fa-search',
}

Vue.use(Vuetify)

const opts = {
  lang: {
    locales: { zhHans, en },
    current: 'zhHans',
  },
  icons: {
    iconfont: 'fa',
    values: MY_ICONS,
  },
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
    VSpacer,
    VAppBar,
  },
}

export default new Vuetify(opts)
