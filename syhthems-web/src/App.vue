<template>
  <v-app
    id="inspire"
    :dark="darkTheme"
  >
    <router-view name="main" />
    <router-view name="toolbar" />
    <v-content>
      <v-container
        fill-height
        fluid
      >
        <v-fade-transition mode="out-in">
          <router-view />
        </v-fade-transition>
      </v-container>
    </v-content>
    <v-footer
      height="auto"
      app
      inset
    >
      <v-layout
        justify-center
        row
        wrap
      >
        <v-flex
          text-md-center
          xs12
          pa-2
        >
          &copy; {{ new Date().getFullYear() }} — <strong>Sunrise YDY</strong>
        </v-flex>
      </v-layout>
    </v-footer>
    <syhthems-loading />
  </v-app>
</template>

<script>
  import SyhthemsMain from './components/SyhthemsMain'

  export default {
    name: 'App',
    components: {
      SyhthemsMain
    },
    data () {
      return {
      //
      }
    }
  }
</script>
