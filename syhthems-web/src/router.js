import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router)

function route (path, name, component, children) {
  return {
    path,
    name,
    components: {
      default: require(`./pages/${component}`).default,
      main: require('./components/SyhthemsMain').default,
      toolbar: require('./components/SyhthemsToolbar').default
    },
    children
  }
}

const router = new Router({
  mode: 'history',
  routes: [
    route('/login', 'login', 'SyhthemsLogin')
  ]
})

export default router
