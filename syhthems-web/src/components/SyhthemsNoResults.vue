<template>
  <v-row>
    <v-col
      cols="12"
      sm="6"
      md="4"
      offset="0"
      offset-sm="3"
      offset-md="4"
    >
      <v-alert
        type="warning"
      >
        <slot>没有符合条件的结果</slot>
      </v-alert>
    </v-col>
  </v-row>
</template>

<script>
  export default {
    name: 'SyhthemsNoResults',
  }
</script>

<style scoped>

</style>
