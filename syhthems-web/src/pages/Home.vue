<template>
  <v-row>
    <v-col
      md="12"
    >
      <v-row
        align="start"
        justify="center"
      >
        <div>
          <h3
            class="display-2"
          >
            欢迎访问 syhthems 物联网平台
          </h3>
        </div>
      </v-row>
    </v-col>
    <v-col
      class="md-12"
    >
      <v-container
        fluid
      >
        <v-data-iterator
          :items="productVOs"
          :custom-filter="searchProduct()"
          hide-default-footer
        >
          <template v-slot:header="{}">
            <v-toolbar
              class="mb-2"
            >
              <v-toolbar-title>产品列表</v-toolbar-title>
              <v-row class="mx-auto hidden-sm-and-down">
                <v-col
                  cols="4"
                  offset="4"
                >
                  <v-text-field
                    v-model="search.name"
                    clearable
                    hide-details
                    placeholder="搜索产品名称"
                    prepend-inner-icon="$vuetify.icons.search"
                  />
                </v-col>
                <v-col
                  cols="4"
                >
                  <v-text-field
                    v-model="search.description"
                    clearable
                    hide-details
                    placeholder="搜索产品简介"
                    prepend-inner-icon="$vuetify.icons.search"
                  />
                </v-col>
              </v-row>
              <v-btn
                icon
                class="mr-2"
                @click="refreshProduct()"
              >
                <v-icon>$vuetify.icons.refresh</v-icon>
              </v-btn>
              <v-dialog
                v-model="newProductDialog"
                persistent
                max-width="400px"
              >
                <template v-slot:activator="{ on }">
                  <v-btn
                    fab
                    dark
                    color="pink"
                    v-on="on"
                  >
                    <v-icon>$vuetify.icons.add</v-icon>
                  </v-btn>
                </template>
                <v-card>
                  <v-card-title>
                    <span class="headline">添加产品</span>
                  </v-card-title>
                  <v-card-text>
                    <v-form
                      ref="newProductForm"
                      v-model="addProductForm.valid"
                      lazy-validation
                    >
                      <v-container>
                        <v-row>
                          <v-col
                            cols="12"
                            sm="6"
                            md="4"
                          >
                            <v-text-field
                              v-model="addProductForm.model.name"
                              :label="addProductForm.fields.name.label"
                              :rules="addProductForm.fields.name.rules"
                              required
                            />
                          </v-col>
                          <v-col
                            cols="12"
                            sm="6"
                            md="8"
                          >
                            <v-text-field
                              v-model="addProductForm.model.description"
                              :label="addProductForm.fields.description.label"
                              :rules="addProductForm.fields.description.rules"
                              counter="100"
                            />
                          </v-col>
                        </v-row>
                      </v-container>
                    </v-form>
                  </v-card-text>
                  <v-card-actions>
                    <v-spacer />
                    <v-btn
                      color="info"
                      @click="clearAddProductForm()"
                    >
                      返回
                    </v-btn>
                    <v-btn
                      color="success"
                      @click="addProduct()"
                    >
                      保存
                    </v-btn>
                  </v-card-actions>
                </v-card>
              </v-dialog>
            </v-toolbar>
          </template>
          <template v-slot:no-data>
            <syhthems-no-data>该账户下没有产品，新建？</syhthems-no-data>
          </template>
          <template v-slot:no-results>
            <syhthems-no-results />
          </template>

          <template v-slot:default="props">
            <v-row>
              <v-col
                v-for="item in props.items"
                :key="item.product.productId"
                cols="12"
                sm="6"
                md="4"
                lg="3"
              >
                <v-card>
                  <v-hover>
                    <v-img slot-scope="{ hover }">
                      <v-expand-transition>
                        <v-card
                          v-if="hover"
                          class="d-flex transition-fast-in-fast-out blue darken-3 v-card--reveal text-center headline white--text"
                          style="height: 100%;"
                          hover
                          raised
                          tile
                          @click="choiceProduct(item)"
                        >
                          <v-card-text class="headline green--text">
                            选择该产品
                          </v-card-text>
                        </v-card>
                      </v-expand-transition>
                      <v-container
                        class="fill-height"
                        fluid
                      >
                        <v-row class="fill-height">
                          <v-col
                            class="align-end flexbox"
                            cols="12"
                          >
                            <h4>{{ item.product.name }}</h4>
                          </v-col>
                        </v-row>
                      </v-container>
                    </v-img>
                  </v-hover>
                  <v-card-text
                    class="body-1"
                  >
                    {{ isEmpty(item.product.description) ? '无产品简介' : item.product.description }}
                  </v-card-text>
                  <v-divider />
                  <v-list dense>
                    <v-list-item>
                      <v-list-item-content>设备:</v-list-item-content>
                      <v-list-item-content class="align-end">
                        {{ isEmpty(item.devices) ? 0 : item.devices.length }} 个
                      </v-list-item-content>
                    </v-list-item>
                    <v-list-item>
                      <v-list-item-content>数据流:</v-list-item-content>
                      <v-list-item-content class="align-end">
                        {{ isEmpty(item.dataStreams) ? 0 : item.dataStreams.length }} 个
                      </v-list-item-content>
                    </v-list-item>
                  </v-list>
                  <v-divider />
                  <v-card-actions>
                    <v-btn
                      dark
                      class="align-start"
                      color="warning"
                      @click="deleteProduct(item.product.productId)"
                    >
                      删除
                    </v-btn>
                  </v-card-actions>
                </v-card>
              </v-col>
            </v-row>
          </template>
        </v-data-iterator>
      </v-container>
    </v-col>
  </v-row>
</template>

<script>
  import qs from 'qs'
  import SyhthemsNoData from '../components/SyhthemsNoData'
  import SyhthemsNoResults from '../components/SyhthemsNoResults'
  const _ = require('lodash/core')

  export default {
    name: 'Home',
    components: { SyhthemsNoResults, SyhthemsNoData },
    data: () => ({
      productVOs: [],
      pagination: {
        rowsPerPage: -1,
      },
      addProductForm: {
        valid: true,
        model: {
          userId: null,
          name: '',
          description: '',
        },
        fields: {
          name: {
            type: 'text',
            label: '产品名',
            required: true,
            rules: [
              v => !!v || '产品名为必输',
            ],
          },
          description: {
            type: 'text',
            label: '产品简介',
            required: false,
            rules: [
              v => (v.length <= 100) || '产品简介最多只能有100个字符',
            ],
          },
        },
      },
      newProductDialog: false,
      search: {
        name: '',
        description: '',
      },
    }),
    mounted () {
      console.log('Home mounted...')
      if (this.$store.state.userDetails) {
        this.getProducts()
      }
    },
    methods: {
      getProducts () {
        this.$store.commit('setGlobalLoading', true)
        this.$http.get('/product').then(res => {
          this.setProducts(res.data.data)
        }).finally(() => setTimeout(() => this.$store.commit('setGlobalLoading', false), 500))
      },
      setProducts (productVOs) {
        this.productVOs = productVOs
      },
      isEmpty (value) {
        return _.isEmpty(value)
      },
      addProduct () {
        if (this.addProductForm.model.name) {
          this.$store.commit('setGlobalLoading', true)
          this.addProductForm.model.userId = this.$store.state.userDetails.user.userId
          this.$http.post('/product', qs.stringify(this.addProductForm.model)).then(res => {
            this.refreshProduct()
          }).catch(reason => {
            //
          }).finally(() => setTimeout(() => this.$store.commit('setGlobalLoading', false), 500))
        }
      },
      clearAddProductForm () {
        this.addProductForm.model = {
          userId: null,
          name: '',
          description: '',
        }
        if (this.$refs.newProductForm && this.$refs.newProductForm.resetValidation) {
          this.$refs.newProductForm.resetValidation()
        }
        this.newProductDialog = false
      },
      deleteProduct (productId) {
        if (productId) {
          this.$dialog.confirm({
            text: '确定要删除这个产品吗？',
            title: '警告!',
            persistent: true,
            waitForResult: true,
            actions: {
              false: {
                flat: false,
                text: '取消',
              },
              true: {
                color: 'red',
                text: '确定',
                flat: false,
              },
            },
          }).then(value => {
            if (value) {
              this.$store.commit('setGlobalLoading', true)
              this.$http.delete(`/product/${productId}`).then(() => {
                this.getProducts()
              }).finally(() => setTimeout(() => this.$store.commit('setGlobalLoading', false), 500))
            }
          })
        }
      },
      choiceProduct (productVO) {
        if (!_.isEmpty(productVO)) {
          // this.$store.commit('setProductVO', productVO)
          // this.$store.commit('setDrawer', true)
          return this.$router.push({ path: `/product/${productVO.product.productId}/` })
        }
      },
      refreshProduct () {
        this.getProducts()
        this.clearAddProductForm()
      },
      searchProduct () {
        return () => {
          if (this.productVOs && this.productVOs.length) {
            console.log('name:' + this.search.name + 'description:' + this.search.description)
            let results = this.productVOs
            if (!_.isEmpty(this.search.name)) {
              console.log('name:' + this.search.name)
              results = results.filter(value => {
                return (value.product.name.search('.*' + this.search.name + '.*') >= 0)
              })
            }
            if (!_.isEmpty(this.search.description)) {
              console.log('description:' + this.search.description)
              results = results.filter(value => {
                return (value.product.description.search('.*' + this.search.description + '.*') >= 0)
              })
            }
            return results
          } else {
            return this.productVOs
          }
        }
      },
    },
  }
</script>

<style scoped>
  .v-card--reveal {
    align-items: center;
    bottom: 0;
    justify-content: center;
    opacity: .7;
    position: absolute;
    width: 100%;
  }
</style>
