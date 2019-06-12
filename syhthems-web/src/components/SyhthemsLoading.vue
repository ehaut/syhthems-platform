<template>
  <v-dialog
    v-model="globalLoading"
    persistent
    width="300"
    content-class="globalLoading"
  >
    <v-card
      color="primary"
      dark
    >
      <v-card-text>
        请稍等...
        <v-progress-linear
          indeterminate
          color="white"
          class="mb-0"
        />
      </v-card-text>
    </v-card>
  </v-dialog>
</template>

<script>
  export default {
    name: 'SyhthemsLoading',
    computed: {
      globalLoading () {
        return this.$store.state.globalLoading
      }
    }
  }
</script>

<style scoped>

</style>
