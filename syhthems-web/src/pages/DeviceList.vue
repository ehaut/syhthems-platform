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
            :to="toProduct"
          >
            <v-icon>$vuetify.icons.prev</v-icon>
            <span>返回产品详情页面</span>
          </v-btn>
          <v-spacer />
          <v-toolbar-title>设备管理</v-toolbar-title>
          <v-spacer />
          <v-btn
            icon
            @click="getDevices()"
          >
            <v-icon>$vuetify.icons.refresh</v-icon>
          </v-btn>
        </v-toolbar>
        <v-container
          fluid
          grid-list-md
        >
          <v-data-iterator
            :items="devices"
            content-tag="v-layout"
            hide-actions
            no-data-text="该产品没有设备"
            row
            wrap
            justify-space-between
          >
            <template v-slot:header="">
              <v-toolbar
                class="mb-2"
              >
                <v-toolbar-title>设备列表</v-toolbar-title>
                <v-spacer />
                <v-dialog
                  v-model="newDeviceDialog"
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
                      <span class="headline">添加设备</span>
                    </v-card-title>
                    <v-card-text>
                      <v-form
                        ref="newDeviceForm"
                        v-model="newDeviceFormScheme.valid"
                        lazy-validation
                      >
                        <v-container>
                          <v-layout wrap>
                            <v-flex
                              xs12
                            >
                              <v-text-field
                                v-model="newDeviceFormScheme.model.name"
                                :label="newDeviceFormScheme.fields.name.label"
                                :rules="newDeviceFormScheme.fields.name.rules"
                                :required="newDeviceFormScheme.fields.name.required"
                              />
                            </v-flex>
                            <v-flex
                              xs12
                            >
                              <v-text-field
                                v-model="newDeviceFormScheme.model.code"
                                :label="newDeviceFormScheme.fields.code.label"
                                :rules="newDeviceFormScheme.fields.code.rules"
                                :required="newDeviceFormScheme.fields.code.required"
                              />
                            </v-flex>
                            <v-flex
                              xs12
                            >
                              <v-text-field
                                v-model="newDeviceFormScheme.model.deviceSecret"
                                :label="newDeviceFormScheme.fields.deviceSecret.label"
                                :rules="newDeviceFormScheme.fields.deviceSecret.rules"
                                :required="newDeviceFormScheme.fields.deviceSecret.required"
                                :hint="newDeviceFormScheme.fields.deviceSecret.hint"
                                :type="newDeviceFormScheme.fields.deviceSecret.type"
                                readonly
                              >
                                <template v-slot:append="">
                                  <v-btn
                                    small
                                    color="primary"
                                    @click="generateDeviceSecret(newDeviceFormScheme.model)"
                                  >
                                    生成密钥
                                  </v-btn>
                                </template>
                              </v-text-field>
                            </v-flex>
                            <v-flex
                              xs12
                            >
                              <v-text-field
                                v-model="newDeviceFormScheme.model.description"
                                :label="newDeviceFormScheme.fields.description.label"
                                :rules="newDeviceFormScheme.fields.description.rules"
                              />
                            </v-flex>
                            <v-flex
                              xs12
                            >
                              <v-text-field
                                v-model="newDeviceFormScheme.model.tags"
                                :label="newDeviceFormScheme.fields.tags.label"
                              />
                            </v-flex>
                          </v-layout>
                        </v-container>
                      </v-form>
                    </v-card-text>
                    <v-card-actions>
                      <v-spacer />
                      <v-btn
                        color="info"
                        @click="clearNewDeviceForm()"
                      >
                        返回
                      </v-btn>
                      <v-btn
                        color="success"
                        @click="addDevice()"
                      >
                        保存
                      </v-btn>
                    </v-card-actions>
                  </v-card>
                </v-dialog>
              </v-toolbar>
            </template>
            <template v-slot:item="props">
              <v-flex
                xs12
                sm6
                md5
              >
                <v-card
                  hover
                  @click="choiceDevice(props.item.deviceId)"
                >
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
                      <v-list-tile-content>设备密钥： </v-list-tile-content>
                      <v-list-tile-content class="align-end">
                        {{ props.item.deviceSecret }}
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
                    <v-divider />
                    <v-list-tile>
                      <v-list-tile-content> 设备简介：</v-list-tile-content>
                      <v-list-tile-content class="align-end">
                        {{ props.item.description }}
                      </v-list-tile-content>
                    </v-list-tile>
                    <v-divider />
                    <v-list-tile>
                      <v-list-tile-content> 设备标签：</v-list-tile-content>
                      <v-list-tile-content class="align-end">
                        {{ props.item.tags }}
                      </v-list-tile-content>
                    </v-list-tile>
                  </v-list>
                  <v-divider />
                  <v-card-actions>
                    <v-btn
                      dark
                      color="warning"
                      @click.stop="deleteDevice(props.item.deviceId)"
                    >
                      删除
                    </v-btn>
                    <v-spacer />
                    <v-btn
                      dark
                      color="info"
                      @click.stop="updateDeviceButton(props.item)"
                    >
                      修改
                    </v-btn>
                  </v-card-actions>
                </v-card>
              </v-flex>
            </template>
          </v-data-iterator>
        </v-container>
      </v-card>
    </v-flex>
    <v-dialog
      v-model="updateDeviceDialog"
      persistent
      max-width="400px"
    >
      <v-card>
        <v-card-title>
          <span class="headline">修改设备</span>
        </v-card-title>
        <v-card-text>
          <v-form
            ref="updateDeviceForm"
            v-model="updateDeviceFormScheme.valid"
            lazy-validation
          >
            <v-container>
              <v-layout wrap>
                <v-flex
                  xs12
                >
                  <v-text-field
                    v-model="updateDeviceFormScheme.model.name"
                    :label="updateDeviceFormScheme.fields.name.label"
                    :rules="updateDeviceFormScheme.fields.name.rules"
                    :required="updateDeviceFormScheme.fields.name.required"
                  />
                </v-flex>
                <v-flex
                  xs12
                >
                  <v-text-field
                    v-model="updateDeviceFormScheme.model.code"
                    :label="updateDeviceFormScheme.fields.code.label"
                    :rules="updateDeviceFormScheme.fields.code.rules"
                    :required="updateDeviceFormScheme.fields.code.required"
                  />
                </v-flex>
                <v-flex
                  xs12
                >
                  <v-text-field
                    v-model="updateDeviceFormScheme.model.deviceSecret"
                    :label="updateDeviceFormScheme.fields.deviceSecret.label"
                    :rules="updateDeviceFormScheme.fields.deviceSecret.rules"
                    :required="updateDeviceFormScheme.fields.deviceSecret.required"
                    :hint="updateDeviceFormScheme.fields.deviceSecret.hint"
                    :type="updateDeviceFormScheme.fields.deviceSecret.type"
                    readonly
                  >
                    <template v-slot:append="">
                      <v-btn
                        small
                        color="primary"
                        @click="generateDeviceSecret(updateDeviceFormScheme.model)"
                      >
                        生成密钥
                      </v-btn>
                    </template>
                  </v-text-field>
                </v-flex>
                <v-flex
                  xs12
                >
                  <v-text-field
                    v-model="updateDeviceFormScheme.model.description"
                    :label="updateDeviceFormScheme.fields.description.label"
                    :rules="updateDeviceFormScheme.fields.description.rules"
                  />
                </v-flex>
                <v-flex
                  xs12
                >
                  <v-text-field
                    v-model="updateDeviceFormScheme.model.tags"
                    :label="updateDeviceFormScheme.fields.tags.label"
                  />
                </v-flex>
              </v-layout>
            </v-container>
          </v-form>
        </v-card-text>
        <v-card-actions>
          <v-spacer />
          <v-btn
            color="info"
            @click="clearUpdateDeviceForm()"
          >
            返回
          </v-btn>
          <v-btn
            color="success"
            @click="updateDevice()"
          >
            保存
          </v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </v-layout>
</template>

<script>
  import qs from 'qs'

  const _ = require('lodash/core')
  const uuidv4 = require('uuid/v4')

  export default {
    name: 'DeviceList',
    data: () => ({
      toHome: { path: '/' },
      newDeviceDialog: false,
      updateDeviceDialog: false,
      newDeviceFormScheme: {
        valid: true,
        model: {
          deviceId: 0,
          deviceSecret: '',
          productId: 0,
          code: '',
          name: '',
          description: '',
          tags: ''
        },
        fields: {
          name: {
            type: 'text',
            label: '设备名称',
            required: true,
            rules: [
              v => !!v || '设备名称必输'
            ]
          },
          code: {
            type: 'text',
            label: '设备编码',
            hint: '设备的唯一识别码',
            required: true,
            rules: [
              v => !!v || '设备编码必输'
            ]
          },
          deviceSecret: {
            type: 'text',
            label: '设备密钥',
            hint: '设备接入平台的凭证',
            required: true,
            rules: [
              v => !!v || '设备密钥必输'
            ]
          },
          description: {
            type: 'text',
            label: '设备简介',
            rules: [
              v => v.length < 256 || '设备简介不能超过256字符'
            ]
          },
          tags: {
            type: 'text',
            label: '设备标签'
          }
        }
      },
      updateDeviceFormScheme: {
        valid: true,
        model: {
          deviceId: '',
          deviceSecret: '',
          productId: 0,
          code: '',
          name: '',
          description: '',
          tags: ''
        },
        fields: {
          name: {
            type: 'text',
            label: '设备名称',
            required: true,
            rules: [
              v => !!v || '设备名称必输'
            ]
          },
          code: {
            type: 'text',
            label: '设备编码',
            hint: '设备的唯一识别码',
            required: true,
            rules: [
              v => !!v || '设备编码必输'
            ]
          },
          deviceSecret: {
            type: 'text',
            label: '设备密钥',
            hint: '设备接入平台的凭证',
            required: true,
            rules: [
              v => !!v || '设备密钥必输'
            ]
          },
          description: {
            type: 'text',
            label: '设备简介',
            rules: [
              v => v.length < 256 || '设备简介不能超过256字符'
            ]
          },
          tags: {
            type: 'text',
            label: '设备标签'
          }
        }
      }
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
      },
      devices: {
        get () {
          return _.isEmpty(this.$store.state.productVO) ? [] : this.$store.state.productVO.devices
        },
        set (value) {
          this.productVO = {
            ...this.productVO,
            devices: value
          }
        }
      },
      productId () {
        return this.$store.getters.productId
      },
      toProduct () {
        return { path: `/product/${this.productId}` }
      }
    },
    created () {
      this.getDevices()
    },
    methods: {
      getDevices () {
        if (this.productId) {
          this.$store.commit('setGlobalLoading', true)
          this.$http.get('/device' + qs.stringify({
            productId: this.productId
          }, {
            addQueryPrefix: true
          })).then(res => {
            if (res.data.code === 0) {
              this.devices = res.data.data
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
      isEmpty (value) {
        return _.isEmpty(value)
      },
      addDevice () {
        if (this.$refs.newDeviceForm.validate()) {
          this.$store.commit('setGlobalLoading', true)
          this.newDeviceFormScheme.model.productId = this.productId
          this.$http.post('/device', qs.stringify(this.newDeviceFormScheme.model)).then(res => {
            this.getDevices()
            this.clearNewDeviceForm()
          }).catch(reason => {
            //
          }).finally(() => setTimeout(() => this.$store.commit('setGlobalLoading', false), 500))
        }
      },
      updateDeviceButton (device) {
        // 一定要用这种方法赋值，返回的是新的对象，而不会修改原来的对象
        this.updateDeviceFormScheme.model = {
          ...device
        }
        this.updateDeviceDialog = true
      },
      updateDevice () {
        if (this.updateDeviceFormScheme.model.deviceId) {
          this.$store.commit('setGlobalLoading', true)
          this.updateDeviceFormScheme.model.productId = this.productId
          this.$http.patch(`/device/${this.updateDeviceFormScheme.model.deviceId}`, qs.stringify(this.updateDeviceFormScheme.model)).then(res => {
            this.getDevices()
            this.clearUpdateDeviceForm()
          }).catch(reason => {
            //
          }).finally(() => setTimeout(() => this.$store.commit('setGlobalLoading', false), 500))
        }
      },
      clearNewDeviceForm () {
        this.newDeviceFormScheme.model = {
          deviceId: '',
          deviceSecret: '',
          productId: 0,
          code: '',
          name: '',
          description: '',
          tags: ''
        }
        this.$refs.newDeviceForm.resetValidation()
        this.newDeviceDialog = false
      },
      clearUpdateDeviceForm () {
        this.updateDeviceFormScheme.model = {
          deviceId: '',
          deviceSecret: '',
          productId: 0,
          code: '',
          name: '',
          description: '',
          tags: ''
        }
        this.$refs.updateDeviceForm.resetValidation()
        this.updateDeviceDialog = false
      },
      deleteDevice (deviceId) {
        if (deviceId) {
          this.$dialog.confirm({
            text: '确定要删除这个设备吗？',
            title: '警告!',
            persistent: true,
            waitForResult: true,
            actions: {
              false: {
                flat: false,
                text: '取消'
              },
              true: {
                color: 'red',
                text: '确定',
                flat: false
              }
            }
          }).then(value => {
            if (value) {
              this.$store.commit('setGlobalLoading', true)
              this.$http.delete(`/device/${deviceId}`).then(() => {
                this.getDevices()
              }).finally(() => setTimeout(() => this.$store.commit('setGlobalLoading', false), 500))
            }
          })
        }
      },
      generateDeviceSecret (model) {
        model.deviceSecret = uuidv4()
      },
      choiceDevice (deviceId) {
        this.$store.commit('setDeviceId', deviceId)
        this.$router.push({ name: 'DEVICE' })
      }
    }
  }
</script>

<style scoped>

</style>
