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
        type="error"
      >
        <slot>没有数据</slot>
      </v-alert>
    </v-col>
  </v-row>
</template>

<script>
  export default {
    name: 'SyhthemsNoData',
  }
</script>

<style scoped>

</style>
