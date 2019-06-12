import Vue from 'vue'
import Vuex from 'vuex'
import menu from './modules/menu'
import config from '../config'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    darkTheme: false,
    userDetails: null,
    config: config,
    globalLoading: false,
    drawer: true,
    mini: false,
    productVO: null,
    devices: [],
    deviceId: false
  },
  modules: {
    menu
  },
  getters: {
    productId: state => {
      return state.productVO ? state.productVO.product.productId : false
    }
  },
  mutations: {
    setDarkTheme (state, darkTheme) {
      state.darkTheme = darkTheme === true
      window.localStorage.setItem('darkTheme', darkTheme)
    },

    setDrawer (state, drawer) {
      state.drawer = drawer
    },

    setMini (state, mini) {
      state.mini = mini
    },

    setUserDetails (state, userDetails) {
      state.userDetails = userDetails
    },

    setProductVO (state, productVO) {
      state.productVO = productVO
    },

    setDevices (state, devices) {
      state.devices = devices
    },

    setDeviceId (state, deviceId) {
      state.deviceId = deviceId
    },

    setGlobalLoading (state, globalLoading) {
      state.globalLoading = globalLoading
    }
  },
  actions: {

  }
})
