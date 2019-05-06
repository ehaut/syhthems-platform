import Vue from 'vue'
import Vuetify from 'vuetify/lib'
import 'vuetify/src/stylus/app.styl'
import zhHans from 'vuetify/es5/locale/zh-Hans'
import 'material-design-icons-iconfont/dist/material-design-icons.css'

Vue.use(Vuetify, {
  lang: {
    locales: zhHans,
    current: 'zhHans'
  },
  iconfont: 'md'
})
