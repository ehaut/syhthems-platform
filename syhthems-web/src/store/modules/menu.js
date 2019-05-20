// state
const state = {
  rawMenus: []
}

// getters
const getters = {
  // 获取指定parentId的rawMenus数组
  childRawMenusof: (state, getters, rootState, rootGetters) => (parentId) => {
    return state.rawMenus.find(rawMenu => rawMenu.parentId === parentId)
  },

  getMenus: (state, getters) => {
    if (state.rawMenus.length === 0) {
      return []
    }
    // 过滤掉类型为按钮的数据
    return getters.rawMenus2Menus(state.rawMenus.filter(rawMenu => rawMenu.type === '0'))
  },

  rawMenus2Menus: (state, getters) => (rawMenus) => {
    if (!rawMenus || !(rawMenus instanceof Array) || rawMenus.length === 0) {
      return []
    } else {
      return rawMenus.map(rawMenu => {
        return {
          menuCode: rawMenu.menuCode,
          menuName: rawMenu.menuName,
          path: rawMenu.path,
          menuComponent: rawMenu.menuComponent,
          icon: rawMenu.icon,
          children: getters.rawMenus2Menus(getters.childRawMenusof(rawMenu.menuId))
        }
      }, getters)
    }
  },

  getRoutes: (state, getters) => {
    // 过滤掉类型为按钮的数据和path为空的菜单
    let filterRawMenus = state.rawMenus.filter(rawMenu => ((rawMenu.type === '0') && rawMenu.path))
    let routes = getters.rawMenus2Routes(filterRawMenus)
    routes.push({
      path: '*',
      components: {
        default: require('../../components/SyhthemsNotFound').default,
        main: require('../../components/SyhthemsMain').default,
        toolbar: require('../../components/SyhthemsToolbar').default
      },
      name: '404'
    })
    return routes
  },

  rawMenus2Routes: (state, getters) => (rawMenus) => {
    if (!rawMenus || !(rawMenus instanceof Array) || rawMenus.length === 0) {
      return []
    } else {
      return rawMenus.map(rawMenu => {
        return {
          path: rawMenu.path,
          components: {
            default: require(`../../components/${rawMenu.menuComponent}`).default,
            main: require('../../components/SyhthemsMain').default,
            toolbar: require('../../components/SyhthemsToolbar').default
          },
          name: rawMenu.menuCode
        }
      }, getters)
    }
  }
}

// actions
const actions = {}

// mutations
const mutations = {
  setRawMenus (state, rawMenus) {
    state.rawMenus = rawMenus
  }
}

export default {
  namespaced: true,
  state,
  getters,
  actions,
  mutations
}
