<template xmlns:v-slot="http://www.w3.org/1999/XSL/Transform">
  <v-toolbar
    clipped-left
    color="blue darken-3"
    dark
    app
    fixed
  >
    <v-toolbar-title
      class="ml-0"
    >
      <v-toolbar-side-icon
        v-show="showDrawerButton"
        @click.stop="drawer = !drawer"
      />
      <v-btn
        v-show="!mini && drawer"
        icon
        flat
        class="hidden-sm-and-down"
        @click.stop="mini = !mini"
      >
        <v-icon>$vuetify.icons.prev</v-icon>
      </v-btn>
      <v-btn
        v-show="mini && drawer"
        icon
        flat
        class="hidden-sm-and-down"
        @click.stop="mini = !mini"
      >
        <v-icon>$vuetify.icons.next</v-icon>
      </v-btn>
      <span class="hidden-sm-and-down">Syhthems 物联网平台</span>
    </v-toolbar-title>
    <v-spacer />
    <v-btn icon>
      <v-icon>$vuetify.icons.apps</v-icon>
    </v-btn>
    <v-btn icon>
      <v-icon>$vuetify.icons.notifications</v-icon>
    </v-btn>
    <v-menu
      open-on-hover
      offset-y
    >
      <template v-slot:activator="{ on }">
        <v-btn
          icon
          large
          v-on="on"
        >
          <v-avatar
            size="32px"
            tile
          >
            <img
              :src="avatarUrl"
              alt="user avatar"
            >
          </v-avatar>
        </v-btn>
      </template>
      <v-list>
        <v-list-tile
          v-if="isAuthenticated"
        >
          <v-list-tile-title
            text-xs-center
            class="body-2 blue--text text--darken-3"
          >
            {{ username }}
          </v-list-tile-title>
        </v-list-tile>
        <v-list-tile
          v-if="isAuthenticated"
          @click="doLogout"
        >
          <v-list-tile-title>登出</v-list-tile-title>
        </v-list-tile>
        <v-list-tile
          v-if="!isAuthenticated"
          to="login"
        >
          <v-list-tile-title>登录</v-list-tile-title>
        </v-list-tile>
      </v-list>
    </v-menu>
  </v-toolbar>
</template>

<script>
  export default {
    name: 'SyhthemsToolbar',
    data: () => ({
      avatarUrl: require('../assets/logo.png')
    }),
    computed: {
      showDrawerButton () {
        return !!this.$store.state.productVO
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

      isAuthenticated () {
        return this.$store.getters['oauth2/isAuthenticated']
      },

      username () {
        return this.isAuthenticated ? this.$oauth2.getLoginUsername() : ''
      }
    },
    mounted () {
      //
    },
    methods: {
      doLogout () {
        this.$oauth2.doLogout()
      }
    }
  }
</script>

<style scoped>

</style>
