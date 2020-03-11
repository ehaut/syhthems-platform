<template>
  <v-navigation-drawer
    v-model="drawer"
    clipped
    :mini-variant.sync="mini"
    fixed
    app
  >
    <v-switch
      v-show="!mini"
      v-model="darkTheme"
      :label="(!darkTheme ? 'Light' : 'Dark') + ' Theme'"
      class="ml-4"
    />
    <v-spacer />
    <v-row
      v-show="loading"
      align="center"
      justify="center"
    >
      <v-col
        class="text-center"
        cols="12"
      >
        <v-progress-circular
          indeterminate
        />
      </v-col>
    </v-row>
    <v-row>
      <v-col md="12">
        <v-card>
          <v-list>
            <template v-for="menu in menus">
              <!-- todo -->
              <v-list-group
                v-if="menu.children && menu.children.length !== 0"
                :key="menu.menuCode"
                :prepend-icon="menu.icon"
              >
                <template v-slot:activator="{}">
                  <v-list-item>
                    <v-list-item-content>
                      <v-list-item-title>{{ menu.menuName }}</v-list-item-title>
                    </v-list-item-content>
                  </v-list-item>
                </template>
                <v-list-item
                  v-for="child in menu.children"
                  :key="child.menuCode"
                  :to="getRouterLink(child)"
                >
                  <v-list-item-action>
                    <v-icon>{{ child.icon }}</v-icon>
                  </v-list-item-action>
                  <v-list-item-content>
                    <v-list-item-title>{{ child.menuName }}</v-list-item-title>
                  </v-list-item-content>
                </v-list-item>
              </v-list-group>
              <v-list-item
                v-else
                :key="menu.menuCode"
                :to="getRouterLink(menu)"
              >
                <v-list-item-action>
                  <v-icon>{{ menu.icon }}</v-icon>
                </v-list-item-action>
                <v-list-item-content>
                  <v-list-item-title>{{ menu.menuName }}</v-list-item-title>
                </v-list-item-content>
              </v-list-item>
            </template>
          </v-list>
        </v-card>
      </v-col>
    </v-row>
  </v-navigation-drawer>
</template>

<script>
  export default {
    name: 'Main',
    data: () => ({
      loading: false,
    }),
    computed: {
      darkTheme: {
        get () {
          return this.$store.state.darkTheme
        },
        set (value) {
          this.$store.commit('setDarkTheme', value)
        },
      },

      drawer: {
        get () {
          return this.$store.state.drawer
        },
        set (value) {
          this.$store.commit('setDrawer', value)
        },
      },

      mini: {
        get () {
          return this.$store.state.mini
        },
        set (value) {
          this.$store.commit('setMini', value)
        },
      },

      menus () {
        return this.$store.getters['menu/getMenus']
      },
    },
    created () {
      console.log('SyhthemsMain created...')

      if (this.$store.state.productVO) {
        this.$store.commit('setDrawer', true)
      } else {
        this.$store.commit('setDrawer', false)
      }
    },
    mounted () {
      console.log('SyhthemsMain mounted...')
    },
    methods: {
      getRouterLink (menu) {
        return (!menu.path || (menu.children && menu.children.length !== 0)) ? undefined : { path: menu.path }
      },
    },

    beforeRouteEnter (to, from, next) {
      next(vm => {
        //
      })
    },
  }
</script>

<style scoped>

</style>
