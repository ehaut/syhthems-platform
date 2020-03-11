<template>
  <v-row>
    <v-col sm="12">
      <v-card>
        <v-toolbar class="mb-2">
          <v-btn
            text
            :to="toHome"
          >
            <v-icon>$vuetify.icons.prev</v-icon>
            <span>  返回产品列表</span>
          </v-btn>
          <v-spacer />
          <v-toolbar-title>产品详情</v-toolbar-title>
          <v-spacer />
          <v-btn
            icon
            @click="getProductVO()"
          >
            <v-icon>$vuetify.icons.refresh</v-icon>
          </v-btn>
        </v-toolbar>
        <v-container
          class="fill-height"
          fluid
        >
          <v-row

            justify="space-between"
          >
            <v-col sm="5">
              <v-card class="my-12">
                <v-card-text class="primary white--text">
                  <h5 class="text-center headline">
                    产品名称
                  </h5>
                </v-card-text>
                <v-divider />
                <v-card-text>{{ productVO ? productVO.product.name : '' }}</v-card-text>
              </v-card>
            </v-col>
            <v-col sm="5">
              <v-card class="my-12">
                <v-card-text class="primary white--text">
                  <h5 class="text-center headline">
                    产品简介
                  </h5>
                </v-card-text>
                <v-divider />
                <v-card-text>{{ productVO ? productVO.product.description : '' }}</v-card-text>
              </v-card>
            </v-col>
            <v-col sm="5">
              <v-card>
                <v-card-text class="text-center primary white--text title">
                  <v-card
                    class="title text-center white--text"
                    :to="toDataStream"
                    color="primary"
                    flat
                    tile
                  >
                    数据流
                  </v-card>
                </v-card-text>
                <v-divider />
                <v-container
                  fluid
                >
                  <v-data-iterator
                    :items="productVO ? productVO.dataStreams : []"
                    hide-default-footer
                    no-data-text="该产品没有数据流"
                    row
                    wrap
                  >
                    <template v-slot:item="props">
                      <v-col
                        cols="12"
                      >
                        <v-card>
                          <v-list dense>
                            <v-list-item>
                              <v-list-item-content class="title">
                                数据流编码：
                              </v-list-item-content>
                              <v-list-item-content class="align-end title">
                                {{ props.item.dataStreamCode }}
                              </v-list-item-content>
                            </v-list-item>
                            <v-divider />
                            <v-list-item>
                              <v-list-item-content>数据流单位： </v-list-item-content>
                              <v-list-item-content class="align-end">
                                {{ props.item.unit }}
                              </v-list-item-content>
                            </v-list-item>
                            <v-divider />
                            <v-list-item>
                              <v-list-item-content>数据流单位符号：</v-list-item-content>
                              <v-list-item-content class="align-end">
                                {{ props.item.unitSymbol }}
                              </v-list-item-content>
                            </v-list-item>
                          </v-list>
                        </v-card>
                      </v-col>
                    </template>
                  </v-data-iterator>
                </v-container>
              </v-card>
            </v-col>
            <v-col sm="5">
              <v-card>
                <v-card-text class="text-center primary white--text title">
                  <v-card
                    class="title text-center white--text"
                    :to="toDevice"
                    color="primary"
                    flat
                    tile
                  >
                    设备
                  </v-card>
                </v-card-text>
                <v-divider />
                <v-container
                  fluid
                >
                  <v-data-iterator
                    :items="productVO ? productVO.devices : []"
                    hide-default-footer
                    no-data-text="该产品没有设备"
                    row
                    wrap
                  >
                    <template v-slot:item="props">
                      <v-col
                        cols="12"
                      >
                        <v-card>
                          <v-list dense>
                            <v-list-item>
                              <v-list-item-content class="title">
                                设备ID：
                              </v-list-item-content>
                              <v-list-item-content class="align-end">
                                {{ props.item.deviceId }}
                              </v-list-item-content>
                            </v-list-item>
                            <v-divider />
                            <v-list-item>
                              <v-list-item-content>设备编号：</v-list-item-content>
                              <v-list-item-content class="align-end">
                                {{ props.item.code }}
                              </v-list-item-content>
                            </v-list-item>
                            <v-divider />
                            <v-list-item>
                              <v-list-item-content>设备名称：</v-list-item-content>
                              <v-list-item-content class="align-end">
                                {{ props.item.name }}
                              </v-list-item-content>
                            </v-list-item>
                          </v-list>
                        </v-card>
                      </v-col>
                    </template>
                  </v-data-iterator>
                </v-container>
              </v-card>
            </v-col>
          </v-row>
        </v-container>
      </v-card>
    </v-col>
  </v-row>
</template>

<script>
  const _ = require('lodash/core')

  export default {
    name: 'Product',
    data: () => ({
      toHome: { path: '/' },
      toDataStream: { path: '/data_stream/' },
      toDevice: { path: '/device/list/' },
    }),
    computed: {
      productVO: {
        get () {
          // return _.isEmpty(this.$store.state.productVO) ? {} : this.$store.state.productVO
          return this.$store.state.productVO
        },
        set (value) {
          this.$store.commit('setProductVO', value)
        },
      },
    },
    watch: {
      '$route' (to, from) {
        // 对路由变化作出响应...
        if (!_.isEmpty(to.params) && to.params.productId) {
          this.getProductVO()
        }
      },
    },
    created () {
      this.getProductVO()
    },
    methods: {
      getProductVO () {
        if (!_.isEmpty(this.$route.params) && this.$route.params.productId) {
          this.$store.commit('setGlobalLoading', true)
          this.$http.get(`/product/${this.$route.params.productId}`).then(res => {
            if (res.data.code === 0 && !_.isEmpty(res.data.data)) {
              this.$store.commit('setProductVO', res.data.data)
              this.updateProductRoute(this.$route.params.productId)
              this.$store.commit('setDrawer', true)
            }
          }).catch().finally(() => setTimeout(() => this.$store.commit('setGlobalLoading', false), 500))
        } else {
          this.$dialog.notify.error(('您还没有选择产品！即将为您跳转到首页'), {
            position: 'bottom-right',
            timeout: 3000,
          })
          this.$router.push({ path: '/' })
        }
      },

      updateProductRoute (productId) {
        if (!productId) {
          productId = this.$route.params.productId
        }
        const newRawMenus = this.$store.state.menu.rawMenus.map((value, index, array) => {
          return {
            ...value,
            path: value.path.replace(':productId', productId),
          }
        })
        this.$store.commit('menu/setRawMenus', newRawMenus)
      },
    },
  }
</script>

<style scoped>

</style>
