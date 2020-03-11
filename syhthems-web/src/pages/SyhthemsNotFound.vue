<template>
  <v-row
    class="fill-height"
    align="center"
    justify="center"
  >
    <v-col
      cols="12"
      sm="6"
    >
      <v-card>
        <div>
          <v-container
            class="fill-height"
            fluid
          >
            <v-row
              class="fill-height"
              align="center"
              justify="center"
            >
              <v-col cols="4">
                <span class="headline">404 Not Found</span>
              </v-col>
            </v-row>
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
    </v-col>
  </v-row>
</template>

<script>
  export default {
    name: 'SyhthemsNotFound',
    data: () => ({
      to: { path: '/' },
    }),
  }
</script>

<style scoped>

</style>
