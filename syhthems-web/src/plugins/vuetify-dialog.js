// https://github.com/yariksav/vuetify-dialog
import vuetify from './vuetify'
import VuetifyDialog from 'vuetify-dialog'
import 'vuetify-dialog/dist/vuetify-dialog.css'
import Vue from 'vue'

Vue.use(VuetifyDialog, {
  context: {
    vuetify,
  },
})
