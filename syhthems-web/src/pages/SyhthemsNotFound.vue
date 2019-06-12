<template>
  <v-layout
    fill-height
    align-center
    justify-center
  >
    <v-flex
      xs12
      sm6
    >
      <v-card>
        <div>
          <v-container
            fill-height
            fluid
          >
            <v-layout
              fill-height
              align-center
              justify-center
            >
              <v-flex xs4>
                <span class="headline">404 Not Found</span>
              </v-flex>
            </v-layout>
          </v-container>
        </div>
        <v-card-title>
          <div>
            <span>你所访问的页面不存在！</span>
          </div>
        </v-card-title>
        <v-card-actions>
          <v-btn
            color="success"
            :to="to"
          >
            回到首页
          </v-btn>
        </v-card-actions>
      </v-card>
    </v-flex>
  </v-layout>
</template>

<script>
  export default {
    name: 'SyhthemsNotFound',
    data: () => ({
      to: { path: '/' }
    })
  }
</script>

<style scoped>

</style>
