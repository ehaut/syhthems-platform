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
          hide-default-footer
          row
          wrap
        >
          <template v-slot:header="">
            <v-toolbar
              class="mb-2"
            >
              <v-toolbar-title>产品列表</v-toolbar-title>
              <v-spacer />
              <v-dialog
                v-model="newProductDialog"
                persistent
                max-width="400px"
              >
                <template v-slot:activator="{ on }">
                  <v-btn
                    icon
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
          <template v-slot:no-data="{ on }">
            <v-col
              cols="12"
              sm="6"
              md="4"
              offset="0"
              offset-sm="3"
              offset-md="4"
              v-on="on"
            >
              <v-alert
                type="error"
              >
                该账户下没有产品，新建？
              </v-alert>
            </v-col>
          </template>

          <template v-slot:item="props">
            <v-col
              cols="12"
              sm="6"
              md="4"
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
                        @click="choiceProduct(props.item)"
                      >
                        <v-card-text>
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
                          <h4>{{ props.item.product.name }}</h4>
                        </v-col>
                      </v-row>
                    </v-container>
                  </v-img>
                </v-hover>
                <v-card-text
                  class="body-1"
                >
                  {{ props.item.product.description }}
                </v-card-text>
                <v-divider />
                <v-list dense>
                  <v-list-item>
                    <v-list-item-content>设备:</v-list-item-content>
                    <v-list-item-content class="align-end">
                      {{ isEmpty(props.item.devices) ? 0 : props.item.devices.length }} 个
                    </v-list-item-content>
                  </v-list-item>
                  <v-list-item>
                    <v-list-item-content>数据流:</v-list-item-content>
                    <v-list-item-content class="align-end">
                      {{ isEmpty(props.item.dataStreams) ? 0 : props.item.dataStreams.length }} 个
                    </v-list-item-content>
                  </v-list-item>
                </v-list>
                <v-divider />
                <v-card-actions>
                  <v-btn
                    dark
                    class="align-start"
                    color="warning"
                    @click="deleteProduct(props.item.product.productId)"
                  >
                    删除
                  </v-btn>
                </v-card-actions>
              </v-card>
            </v-col>
          </template>
        </v-data-iterator>
      </v-container>
    </v-col>
  </v-row>
</template>

<script>
  import qs from 'qs'
  const _ = require('lodash/core')

  export default {
    name: 'Home',
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
            label: '产品描述',
            required: false,
            rules: [
              v => (v.length <= 100) || '产品描述最多只能有100个字符',
            ],
          },
        },
      },
      newProductDialog: false,
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
            this.getProducts()
            this.clearAddProductForm()
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
        this.$refs.newProductForm.resetValidation()
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
