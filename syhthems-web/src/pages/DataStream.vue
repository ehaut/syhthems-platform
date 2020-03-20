<template>
  <v-row>
    <v-col sm="12">
      <v-card>
        <v-toolbar class="mb-2">
          <v-btn
            text
            :to="toProduct"
          >
            <v-icon>$vuetify.icons.prev</v-icon>
            <span>返回产品详情页面</span>
          </v-btn>
          <v-spacer />
          <v-toolbar-title>数据流模板管理</v-toolbar-title>
          <v-spacer />
          <v-btn
            icon
            @click="getDataStreams()"
          >
            <v-icon>$vuetify.icons.refresh</v-icon>
          </v-btn>
        </v-toolbar>
        <v-container
          fluid
        >
          <v-data-iterator
            :items="dataStreams"
            hide-default-footer
            no-data-text="该产品没有数据流"
            row
            wrap
          >
            <template v-slot:header>
              <v-toolbar
                class="mb-2"
              >
                <v-toolbar-title>数据流列表</v-toolbar-title>
                <v-spacer />
                <v-dialog
                  v-model="newDataStreamDialog"
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
                      <span class="headline">添加数据流</span>
                    </v-card-title>
                    <v-card-text>
                      <v-form
                        ref="newDataStreamForm"
                        v-model="newDataStreamFormScheme.valid"
                        lazy-validation
                      >
                        <v-container>
                          <v-row>
                            <v-col
                              cols="12"
                            >
                              <v-text-field
                                v-model="newDataStreamFormScheme.model.dataStreamCode"
                                :label="newDataStreamFormScheme.fields.dataStreamCode.label"
                                :rules="newDataStreamFormScheme.fields.dataStreamCode.rules"
                                :required="newDataStreamFormScheme.fields.dataStreamCode.required"
                                :hint="newDataStreamFormScheme.fields.dataStreamCode.hint"
                                :type="newDataStreamFormScheme.fields.dataStreamCode.type"
                              />
                            </v-col>
                            <v-col
                              cols="12"
                              sm="6"
                            >
                              <v-text-field
                                v-model="newDataStreamFormScheme.model.unit"
                                :label="newDataStreamFormScheme.fields.unit.label"
                                :rules="newDataStreamFormScheme.fields.unit.rules"
                              />
                            </v-col>
                            <v-col
                              cols="12"
                              sm="6"
                            >
                              <v-text-field
                                v-model="newDataStreamFormScheme.model.unitSymbol"
                                :label="newDataStreamFormScheme.fields.unitSymbol.label"
                                :rules="newDataStreamFormScheme.fields.unitSymbol.rules"
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
                        @click="clearNewDataStreamForm()"
                      >
                        返回
                      </v-btn>
                      <v-btn
                        color="success"
                        @click="addDataStream()"
                      >
                        保存
                      </v-btn>
                    </v-card-actions>
                  </v-card>
                </v-dialog>
              </v-toolbar>
            </template>
            <template v-slot:default="props">
              <v-row>
                <v-col
                  v-for="item in props.items"
                  :key="item.dataStreamId"
                  cols="12"
                  sm="6"
                  md="4"
                  lg="3"
                >
                  <v-card>
                    <v-list dense>
                      <v-list-item>
                        <v-list-item-content class="title">
                          数据流编码：
                        </v-list-item-content>
                        <v-list-item-content class="align-end title">
                          {{ item.dataStreamCode }}
                        </v-list-item-content>
                      </v-list-item>
                      <v-divider />
                      <v-list-item>
                        <v-list-item-content>数据流单位： </v-list-item-content>
                        <v-list-item-content class="align-end">
                          {{ item.unit }}
                        </v-list-item-content>
                      </v-list-item>
                      <v-divider />
                      <v-list-item>
                        <v-list-item-content>数据流单位符号：</v-list-item-content>
                        <v-list-item-content class="align-end">
                          {{ item.unitSymbol }}
                        </v-list-item-content>
                      </v-list-item>
                    </v-list>
                    <v-divider />
                    <v-card-actions>
                      <v-btn
                        dark
                        color="warning"
                        @click="deleteDataStream(item.dataStreamId)"
                      >
                        删除
                      </v-btn>
                      <v-spacer />
                      <v-btn
                        dark
                        color="info"
                        @click.stop="updateDataStreamButton(item)"
                      >
                        修改
                      </v-btn>
                    </v-card-actions>
                  </v-card>
                </v-col>
              </v-row>
            </template>
          </v-data-iterator>
        </v-container>
      </v-card>
    </v-col>
    <v-dialog
      v-model="updateDataStreamDialog"
      persistent
      max-width="400px"
    >
      <v-card>
        <v-card-title>
          <span class="headline">修改数据流</span>
        </v-card-title>
        <v-card-text>
          <v-form
            ref="updateDataStreamForm"
            v-model="updateDataStreamFormScheme.valid"
            lazy-validation
          >
            <v-container>
              <v-row>
                <v-col
                  cols="12"
                >
                  <v-text-field
                    v-model="updateDataStreamFormScheme.model.dataStreamCode"
                    :label="updateDataStreamFormScheme.fields.dataStreamCode.label"
                    :rules="updateDataStreamFormScheme.fields.dataStreamCode.rules"
                    :required="updateDataStreamFormScheme.fields.dataStreamCode.required"
                    :hint="updateDataStreamFormScheme.fields.dataStreamCode.hint"
                    :type="updateDataStreamFormScheme.fields.dataStreamCode.type"
                  />
                </v-col>
                <v-col
                  cols="12"
                  sm="6"
                >
                  <v-text-field
                    v-model="updateDataStreamFormScheme.model.unit"
                    :label="updateDataStreamFormScheme.fields.unit.label"
                    :rules="updateDataStreamFormScheme.fields.unit.rules"
                  />
                </v-col>
                <v-col
                  cols="12"
                  sm="6"
                >
                  <v-text-field
                    v-model="updateDataStreamFormScheme.model.unitSymbol"
                    :label="updateDataStreamFormScheme.fields.unitSymbol.label"
                    :rules="updateDataStreamFormScheme.fields.unitSymbol.rules"
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
            @click="clearUpdateDataStreamForm()"
          >
            返回
          </v-btn>
          <v-btn
            color="success"
            @click="updateDataStream()"
          >
            保存
          </v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </v-row>
</template>

<script>
  import qs from 'qs'

  const _ = require('lodash/core')

  export default {
    name: 'DataStream',
    data: () => ({
      toHome: { path: '/' },
      newDataStreamDialog: false,
      updateDataStreamDialog: false,
      newDataStreamFormScheme: {
        valid: true,
        model: {
          productId: 0,
          dataStreamId: '',
          dataStreamCode: '',
          unit: '',
          unitSymbol: '',
        },
        fields: {
          dataStreamCode: {
            type: 'text',
            label: '数据流编码',
            hint: '设备上传数据时的参数名',
            required: true,
            rules: [
              v => !!v || '数据流编码必输',
            ],
          },
          unit: {
            type: 'text',
            label: '数据流单位',
            required: true,
            rules: [
              v => !!v || '数据流单位必输',
            ],
          },
          unitSymbol: {
            type: 'text',
            label: '数据流单位符号',
            required: true,
            rules: [
              v => !!v || '数据流单位符号必输',
            ],
          },
        },
      },
      updateDataStreamFormScheme: {
        valid: true,
        model: {
          productId: 0,
          dataStreamId: '',
          dataStreamCode: '',
          unit: '',
          unitSymbol: '',
        },
        fields: {
          dataStreamCode: {
            type: 'text',
            label: '数据流编码',
            hint: '设备上传数据时的参数名',
            required: true,
            rules: [
              v => !!v || '数据流编码必输',
            ],
          },
          unit: {
            type: 'text',
            label: '数据单位',
            required: true,
            rules: [
              v => !!v || '数据单位必输',
            ],
          },
          unitSymbol: {
            type: 'text',
            label: '数据单位符号',
            required: true,
            rules: [
              v => !!v || '数据单位符号必输',
            ],
          },
        },
      },
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
      dataStreams: {
        get () {
          return _.isEmpty(this.$store.state.productVO) ? [] : this.$store.state.productVO.dataStreams
        },
        set (value) {
          this.productVO = {
            ...this.productVO,
            dataStreams: value,
          }
        },
      },
      productId () {
        return this.$store.getters.productId
      },
      toProduct () {
        return { path: `/product/${this.productId}` }
      },
    },
    created () {
      this.getDataStreams()
    },
    methods: {
      getDataStreams () {
        if (this.productId) {
          this.$store.commit('setGlobalLoading', true)
          this.$http.get('/ds' + qs.stringify({
            productId: this.productId,
          }, {
            addQueryPrefix: true,
          })).then(res => {
            if (res.data.code === 0) {
              this.dataStreams = res.data.data
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
      isEmpty (value) {
        return _.isEmpty(value)
      },
      addDataStream () {
        if (this.newDataStreamFormScheme.model.dataStreamCode) {
          this.$store.commit('setGlobalLoading', true)
          this.newDataStreamFormScheme.model.productId = this.productId
          this.$http.post('/ds', qs.stringify(this.newDataStreamFormScheme.model)).then(res => {
            this.getDataStreams()
            this.clearNewDataStreamForm()
          }).catch(reason => {
            //
          }).finally(() => setTimeout(() => this.$store.commit('setGlobalLoading', false), 500))
        }
      },
      updateDataStreamButton (dataStream) {
        // 一定要用这种方法赋值，返回的是新的对象，而不会修改原来的对象
        this.updateDataStreamFormScheme.model = {
          ...dataStream,
        }
        this.updateDataStreamDialog = true
      },
      updateDataStream () {
        if (this.updateDataStreamFormScheme.model.dataStreamId && this.updateDataStreamFormScheme.model.dataStreamCode) {
          this.$store.commit('setGlobalLoading', true)
          this.updateDataStreamFormScheme.model.productId = this.productId
          this.$http.patch(`/ds/${this.updateDataStreamFormScheme.model.dataStreamId}`, qs.stringify(this.updateDataStreamFormScheme.model)).then(res => {
            this.getDataStreams()
            this.clearUpdateDataStreamForm()
          }).catch(reason => {
            //
          }).finally(() => setTimeout(() => this.$store.commit('setGlobalLoading', false), 500))
        }
      },
      clearNewDataStreamForm () {
        this.newDataStreamFormScheme.model = {
          productId: this.productId,
          dataStreamId: '',
          dataStreamCode: '',
          unit: '',
          unitSymbol: '',
        }
        this.$refs.newDataStreamForm.resetValidation()
        this.newDataStreamDialog = false
      },
      clearUpdateDataStreamForm () {
        this.updateDataStreamFormScheme.model = {
          productId: this.productId,
          dataStreamId: '',
          dataStreamCode: '',
          unit: '',
          unitSymbol: '',
        }
        this.$refs.updateDataStreamForm.resetValidation()
        this.updateDataStreamDialog = false
      },
      deleteDataStream (dataStreamId) {
        if (dataStreamId) {
          this.$dialog.confirm({
            text: '确定要删除这个数据流吗？',
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
              this.$http.delete(`/ds/${dataStreamId}`).then(() => {
                this.getDataStreams()
              }).finally(() => setTimeout(() => this.$store.commit('setGlobalLoading', false), 500))
            }
          })
        }
      },
    },
  }
</script>

<style scoped>

</style>
