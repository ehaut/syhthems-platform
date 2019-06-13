<template xmlns:v-slot="http://www.w3.org/1999/XSL/Transform">
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
      class="ml-3"
    />
    <v-spacer />
    <v-layout
      v-show="loading"
      align-center
      justify-center
      column
    >
      <v-flex
        xs12
        text-xs-center
      >
        <v-progress-circular
          indeterminate
        />
      </v-flex>
    </v-layout>
    <v-layout row>
      <v-flex md12>
        <v-card>
          <v-list>
            <template v-for="menu in menus">
              <v-list-group
                v-if="menu.children && menu.children.length !== 0"
                :key="menu.menuCode"
                :prepend-icon="menu.icon"
              >
                <template v-slot:activator="">
                  <v-list-tile>
                    <v-list-tile-content>
                      <v-list-tile-title>{{ menu.menuName }}</v-list-tile-title>
                    </v-list-tile-content>
                  </v-list-tile>
                </template>
                <v-list-tile
                  v-for="child in menu.children"
                  :key="child.menuCode"
                  :to="getRouterLink(child)"
                >
                  <v-list-tile-action>
                    <v-icon>{{ child.icon }}</v-icon>
                  </v-list-tile-action>
                  <v-list-tile-content>
                    <v-list-tile-title>{{ child.menuName }}</v-list-tile-title>
                  </v-list-tile-content>
                </v-list-tile>
              </v-list-group>
              <v-list-tile
                v-else
                :key="menu.menuCode"
                :to="getRouterLink(menu)"
              >
                <v-list-tile-action>
                  <v-icon>{{ menu.icon }}</v-icon>
                </v-list-tile-action>
                <v-list-tile-content>
                  <v-list-tile-title>{{ menu.menuName }}</v-list-tile-title>
                </v-list-tile-content>
              </v-list-tile>
            </template>
          </v-list>
        </v-card>
      </v-flex>
    </v-layout>
  </v-navigation-drawer>
</template>

<script>
  export default {
    name: 'Main',
    data: () => ({
      loading: false
    }),
    computed: {
      darkTheme: {
        get () {
          return this.$store.state.darkTheme
        },
        set (value) {
          this.$store.commit('setDarkTheme', value)
        }
      },

      drawer: {
        get () {
          return this.$store.state.drawer
        },
        set (value) {
          this.$store.commit('setDrawer', value)
        }
      },

      mini: {
        get () {
          return this.$store.state.mini
        },
        set (value) {
          this.$store.commit('setMini', value)
        }
      },

      menus () {
        return this.$store.getters['menu/getMenus']
      }
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
      }
    },

    beforeRouteEnter (to, from, next) {
      next(vm => {
        //
      })
    }
  }
</script>

<style scoped>

</style>
