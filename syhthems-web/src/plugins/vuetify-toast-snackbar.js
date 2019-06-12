// https://github.com/eolant/vuetify-toast-snackbar
import VuetifyToast from 'vuetify-toast-snackbar'
import Vue from 'vue'

Vue.use(VuetifyToast, {
  x: 'right', // default
  y: 'bottom', // default
  color: 'info', // default
  classes: [
    'title'
  ],
  timeout: 3000, // default
  dismissable: true, // default
  autoHeight: true, // default false
  multiLine: false, // default
  vertical: false, // default
  queueable: false, // default
  shorts: {
    info: {
      color: 'info',
      icon: 'info'
    },
    success: {
      color: 'success',
      icon: 'check_circle'
    },
    warning: {
      color: 'warning',
      icon: 'warning'
    },
    error: {
      color: 'error',
      icon: 'error'
    }
  },
  property: '$toast' // default
})
