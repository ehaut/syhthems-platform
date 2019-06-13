<template xmlns:v-slot="http://www.w3.org/1999/XSL/Transform">
  <v-layout
    row
    wrap
  >
    <v-flex sm12>
      <v-card>
        <v-toolbar class="mb-2">
          <v-btn
            flat
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
          fill-height
          fluid
        >
          <v-layout
            row
            wrap
            justify-space-between
          >
            <v-flex sm5>
              <v-card class="my-5">
                <v-card-text class="primary white--text">
                  <h5 class="text-xs-center headline">
                    产品名称
                  </h5>
                </v-card-text>
                <v-divider />
                <v-card-text>{{ productVO ? productVO.product.name : '' }}</v-card-text>
              </v-card>
            </v-flex>
            <v-flex sm5>
              <v-card class="my-5">
                <v-card-text class="primary white--text">
                  <h5 class="text-xs-center headline">
                    产品简介
                  </h5>
                </v-card-text>
                <v-divider />
                <v-card-text>{{ productVO ? productVO.product.description : '' }}</v-card-text>
              </v-card>
            </v-flex>
            <v-flex sm5>
              <v-card>
                <v-card-text class="text-xs-center primary white--text title">
                  <v-card
                    class="title text-xs-center white--text"
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
                  grid-list-md
                >
                  <v-data-iterator
                    :items="productVO ? productVO.dataStreams : []"
                    content-tag="v-layout"
                    hide-actions
                    no-data-text="该产品没有数据流"
                    row
                    wrap
                  >
                    <template v-slot:item="props">
                      <v-flex
                        xs12
                      >
                        <v-card>
                          <v-list dense>
                            <v-list-tile>
                              <v-list-tile-content class="title">
                                数据流编码：
                              </v-list-tile-content>
                              <v-list-tile-content class="align-end title">
                                {{ props.item.dataStreamCode }}
                              </v-list-tile-content>
                            </v-list-tile>
                            <v-divider />
                            <v-list-tile>
                              <v-list-tile-content>数据流单位： </v-list-tile-content>
                              <v-list-tile-content class="align-end">
                                {{ props.item.unit }}
                              </v-list-tile-content>
                            </v-list-tile>
                            <v-divider />
                            <v-list-tile>
                              <v-list-tile-content>数据流单位符号：</v-list-tile-content>
                              <v-list-tile-content class="align-end">
                                {{ props.item.unitSymbol }}
                              </v-list-tile-content>
                            </v-list-tile>
                          </v-list>
                        </v-card>
                      </v-flex>
                    </template>
                  </v-data-iterator>
                </v-container>
              </v-card>
            </v-flex>
            <v-flex sm5>
              <v-card>
                <v-card-text class="text-xs-center primary white--text title">
                  <v-card
                    class="title text-xs-center white--text"
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
                  grid-list-md
                >
                  <v-data-iterator
                    :items="productVO ? productVO.devices : []"
                    content-tag="v-layout"
                    hide-actions
                    no-data-text="该产品没有设备"
                    row
                    wrap
                  >
                    <template v-slot:item="props">
                      <v-flex
                        xs12
                      >
                        <v-card>
                          <v-list dense>
                            <v-list-tile>
                              <v-list-tile-content class="title">
                                设备ID：
                              </v-list-tile-content>
                              <v-list-tile-content class="align-end">
                                {{ props.item.deviceId }}
                              </v-list-tile-content>
                            </v-list-tile>
                            <v-divider />
                            <v-list-tile>
                              <v-list-tile-content>设备编号：</v-list-tile-content>
                              <v-list-tile-content class="align-end">
                                {{ props.item.code }}
                              </v-list-tile-content>
                            </v-list-tile>
                            <v-divider />
                            <v-list-tile>
                              <v-list-tile-content>设备名称：</v-list-tile-content>
                              <v-list-tile-content class="align-end">
                                {{ props.item.name }}
                              </v-list-tile-content>
                            </v-list-tile>
                          </v-list>
                        </v-card>
                      </v-flex>
                    </template>
                  </v-data-iterator>
                </v-container>
              </v-card>
            </v-flex>
          </v-layout>
        </v-container>
      </v-card>
    </v-flex>
  </v-layout>
</template>

<script>
  const _ = require('lodash/core')

  export default {
    name: 'Product',
    data: () => ({
      toHome: { path: '/' },
      toDataStream: { path: '/data_stream/' },
      toDevice: { path: '/device/list/' }
    }),
    computed: {
      productVO: {
        get () {
          // return _.isEmpty(this.$store.state.productVO) ? {} : this.$store.state.productVO
          return this.$store.state.productVO
        },
        set (value) {
          this.$store.commit('setProductVO', value)
        }
      }
    },
    watch: {
      '$route' (to, from) {
        // 对路由变化作出响应...
        if (!_.isEmpty(to.params) && to.params.productId) {
          this.getProductVO()
        }
      }
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
            timeout: 3000
          })
          this.$router.push({ path: '/' })
        }
      },

      updateProductRoute (productId) {
        if (!productId) {
          productId = this.$route.params.productId
        }
        let newRawMenus = this.$store.state.menu.rawMenus.map((value, index, array) => {
          return {
            ...value,
            path: value.path.replace(':productId', productId)
          }
        })
        this.$store.commit('menu/setRawMenus', newRawMenus)
      }
    }
  }
</script>

<style scoped>

</style>
