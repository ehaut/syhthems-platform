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
            :to="toDeviceList"
          >
            <v-icon>$vuetify.icons.prev</v-icon>
            <span>  返回设备列表</span>
          </v-btn>
          <v-spacer />
          <v-toolbar-title>设备详情</v-toolbar-title>
          <v-spacer />
          <v-btn
            icon
            @click="getDeviceVO()"
          >
            <v-icon>$vuetify.icons.refresh</v-icon>
          </v-btn>
        </v-toolbar>
        <v-divider />
        <v-layout
          row
          wrap
          class="ma-2"
        >
          <v-flex
            xs12
            md4
          >
            <v-card>
              <v-list dense>
                <v-list-tile>
                  <v-list-tile-content class="title">
                    设备ID：
                  </v-list-tile-content>
                  <v-list-tile-content class="align-end">
                    {{ deviceId ? deviceId : 0 }}
                  </v-list-tile-content>
                </v-list-tile>
                <v-divider />
                <v-list-tile>
                  <v-list-tile-content>设备密钥： </v-list-tile-content>
                  <v-list-tile-content class="align-end">
                    {{ deviceVO ? deviceVO.device.deviceSecret : '' }}
                  </v-list-tile-content>
                </v-list-tile>
              </v-list>
            </v-card>
          </v-flex>
          <v-flex
            xs12
            md4
          >
            <v-card>
              <v-list dense>
                <v-list-tile>
                  <v-list-tile-content>设备编号：</v-list-tile-content>
                  <v-list-tile-content class="align-end">
                    {{ deviceVO ? deviceVO.device.code : '' }}
                  </v-list-tile-content>
                </v-list-tile>
                <v-divider />
                <v-list-tile>
                  <v-list-tile-content>设备名称：</v-list-tile-content>
                  <v-list-tile-content class="align-end">
                    {{ deviceVO ? deviceVO.device.name : '' }}
                  </v-list-tile-content>
                </v-list-tile>
              </v-list>
            </v-card>
          </v-flex>
          <v-flex
            xs12
            md4
          >
            <v-card>
              <v-list dense>
                <v-list-tile>
                  <v-list-tile-content> 设备简介：</v-list-tile-content>
                  <v-list-tile-content class="align-end">
                    {{ deviceVO ? deviceVO.device.description : '' }}
                  </v-list-tile-content>
                </v-list-tile>
                <v-divider />
                <v-list-tile>
                  <v-list-tile-content> 设备标签：</v-list-tile-content>
                  <v-list-tile-content class="align-end">
                    {{ deviceVO ? deviceVO.device.tags: '' }}
                  </v-list-tile-content>
                </v-list-tile>
              </v-list>
            </v-card>
          </v-flex>
        </v-layout>
        <v-divider />
        <v-layout
          row
          wrap
        >
          <v-flex
            xs12
          >
            <v-data-iterator
              :items="deviceVO ? deviceVO.dataStreamVOs : []"
              item-key="dataStream.dataStreamCode"
              content-tag="v-layout"
              :pagination.sync="pagination"
              :rows-per-page-items="rowsPerPageItems"
              :total-items="total"
              :hide-actions="!isExpanded"
              no-data-text="该设备没有绑定数据流"
              :expand="expand"
              row
              wrap
            >
              <template v-slot:item="props">
                <v-flex
                  xs12
                  class="ma-2"
                >
                  <v-card>
                    <v-toolbar>
                      <v-toolbar-title>
                        {{ props.item.dataStream.dataStreamCode }}  单位：{{ props.item.dataStream.unit }} ({{ props.item.dataStream.unitSymbol }})
                      </v-toolbar-title>
                      <v-spacer />
                      <v-btn
                        v-if="props.expanded"
                        fab
                        flat
                        @click="expandCharts(props)"
                      >
                        <v-icon>$vuetify.icons.up</v-icon>
                      </v-btn>
                      <v-btn
                        v-else
                        fab
                        flat
                        @click="expandCharts(props)"
                      >
                        <v-icon>$vuetify.icons.down</v-icon>
                      </v-btn>
                    </v-toolbar>
                    <v-divider />
                    <v-card-text
                      v-if="props.expanded"
                    >
                      <v-card>
                        <v-toolbar class="pt-2">
                          <v-layout
                            row
                            wrap
                          >
                            <v-flex
                              xs12
                              sm6
                              md3
                            >
                              <v-menu
                                v-model="startDateMenu"
                                :close-on-content-click="false"
                                :nudge-right="40"
                                lazy
                                transition="scale-transition"
                                offset-y
                                full-width
                                min-width="290px"
                              >
                                <template v-slot:activator="{ on }">
                                  <v-text-field
                                    v-model="startDate"
                                    label="开始日期"
                                    readonly
                                    class="ma-2"
                                    v-on="on"
                                  />
                                </template>
                                <v-date-picker
                                  v-model="startDate"
                                  :first-day-of-week="1"
                                  locale="zh-cn"
                                  :max="endDate"
                                  @input="startDateMenu = false"
                                />
                              </v-menu>
                            </v-flex>
                            <v-flex
                              xs12
                              sm6
                              md3
                            >
                              <v-menu
                                ref="startTimeMenu"
                                v-model="startTimeMenuModel"
                                :close-on-content-click="false"
                                :nudge-right="40"
                                :return-value.sync="startTime"
                                lazy
                                transition="scale-transition"
                                offset-y
                                full-width
                                max-width="290px"
                                min-width="290px"
                              >
                                <template v-slot:activator="{ on }">
                                  <v-text-field
                                    v-model="startTime"
                                    label="开始时间"
                                    readonly
                                    class="ma-2"
                                    v-on="on"
                                  />
                                </template>
                                <v-time-picker
                                  v-if="startTimeMenuModel"
                                  v-model="startTime"
                                  full-width
                                  format="24hr"
                                  @click:minute="$refs.startTimeMenu.save(startTime)"
                                />
                              </v-menu>
                            </v-flex>
                            <v-flex
                              xs12
                              sm6
                              md3
                            >
                              <v-menu
                                v-model="endDateMenu"
                                :close-on-content-click="false"
                                :nudge-right="40"
                                lazy
                                transition="scale-transition"
                                offset-y
                                full-width
                                min-width="290px"
                              >
                                <template v-slot:activator="{ on }">
                                  <v-text-field
                                    v-model="endDate"
                                    label="结束日期"
                                    readonly
                                    class="ma-2"
                                    v-on="on"
                                  />
                                </template>
                                <v-date-picker
                                  v-model="endDate"
                                  :first-day-of-week="1"
                                  locale="zh-cn"
                                  :min="startDate"
                                  @input="endDateMenu = false"
                                />
                              </v-menu>
                            </v-flex>
                            <v-flex
                              xs12
                              sm6
                              md3
                            >
                              <v-menu
                                ref="endTimeMenu"
                                v-model="endTimeMenuModel"
                                :close-on-content-click="false"
                                :nudge-right="40"
                                :return-value.sync="endTime"
                                lazy
                                transition="scale-transition"
                                offset-y
                                full-width
                                max-width="290px"
                                min-width="290px"
                              >
                                <template v-slot:activator="{ on }">
                                  <v-text-field
                                    v-model="endTime"
                                    label="结束时间"
                                    readonly
                                    class="ma-2"
                                    v-on="on"
                                  />
                                </template>
                                <v-time-picker
                                  v-if="endTimeMenuModel"
                                  v-model="endTime"
                                  full-width
                                  format="24hr"
                                  @click:minute="$refs.endTimeMenu.save(endTime)"
                                />
                              </v-menu>
                            </v-flex>
                          </v-layout>
                          <v-btn
                            color="primary"
                            @click="selectDataPoints(props.item)"
                          >
                            查询
                          </v-btn>
                        </v-toolbar>
                        <v-divider />
                        <ve-line
                          :data="chartData"
                          :settings="chartSettings"
                          :height="height"
                        />
                      </v-card>
                    </v-card-text>
                  </v-card>
                </v-flex>
              </template>
            </v-data-iterator>
          </v-flex>
        </v-layout>
      </v-card>
    </v-flex>
  </v-layout>
</template>

<script>
  const _ = require('lodash/core')

  export default {
    name: 'Device',
    data: () => ({
      deviceVO: false,
      toDeviceList: { path: '/device/list/' },
      expand: false,
      rowsPerPageItems: [50, 100, 200],
      dataPointQueryVO: {
        deviceId: 0,
        dataStreamId: 0,
        startDateTime: '',
        endDateTime: '',
        rowsPerPage: 50,
        page: 1
      },
      total: 30,
      pagination: {
        page: 1,
        rowsPerPage: 50
      },
      isExpanded: false,
      startDateMenu: false,
      startTimeMenuModel: false,
      endDateMenu: false,
      endTimeMenuModel: false,
      startDate: new Date(0).toISOString().substr(0, 10),
      startTime: new Date(0).toISOString().substr(11, 5),
      endDate: new Date().toISOString().substr(0, 10),
      endTime: new Date().toISOString().substr(11, 5),
      chartData: {
        columns: ['dataPointTimestamp', 'dataStreamCode'],
        rows: [
          { 'dataPointTimestamp': '2018-01-01', 'dataStreamCode': '' }
        ]
      },
      chartSettings: {
        xAxisType: 'time'
      },
      height: '400px'
    }),
    computed: {
      deviceId: {
        get () {
          return this.$store.state.deviceId
        },
        set (value) {
          this.$store.commit('setDeviceId', value)
        }
      },
      productId () {
        return this.$store.getters.productId
      },
      toProduct () {
        return { path: `/product/${this.productId}` }
      }
    },
    watch: {
      pagination: {
        handler (newValue, oldValue) {
          this.selectDataPoints()
        },
        deep: true
      }
    },
    created () {
      if (!this.$store.getters.productId) {
        this.$dialog.notify.error(('您还没有选择产品！即将为您跳转到首页'), {
          position: 'bottom-right',
          timeout: 3000
        })
        this.$router.push({ path: '/' })
      } else if (!this.$store.state.deviceId) {
        this.$dialog.notify.error(('您还没有选择设备！即将为您跳转到设备列表页面'), {
          position: 'bottom-right',
          timeout: 3000
        })
        this.$router.push({ path: '/device/list/' })
      } else {
        this.getDeviceVO()
      }
    },
    methods: {
      getDeviceVO () {
        if (this.$store.state.deviceId) {
          this.$store.commit('setGlobalLoading', true)
          this.$http.get(`/device/${this.$store.state.deviceId}`).then(res => {
            if (res.data.code === 0) {
              this.deviceVO = _.isEmpty(res.data.data) ? false : res.data.data
              if (this.deviceVO.dataStreamVOs === null || this.deviceVO.dataStreamVOs.length === 0) {
                this.$dialog.notify.error(('当前设备没有绑定数据流，请先绑定数据流'), {
                  position: 'bottom-right',
                  timeout: 3000
                })
                this.$router.push({ name: 'DATA_STREAMS' })
              }
            }
          }).catch().finally(() => setTimeout(() => this.$store.commit('setGlobalLoading', false), 500))
        } else {
          this.deviceVO = false
        }
      },
      expandCharts (props) {
        props.expanded = !props.expanded
        this.isExpanded = props.expanded
        if (this.isExpanded) {
          this.total = props.item.totalDataPoint
          let code = props.item.dataStream.dataStreamCode
          this.chartData.columns = [ '时间', code ]
          this.chartData.rows = props.item.dataPoints.map(value => {
            let ob = {}
            ob['时间'] = new Date(value.dataPointTimestamp)
            ob[code] = value.dataPointData
            return ob
          })
          this.dataPointQueryVO.startDateTime = this.contactDataAndTime(this.startDate, this.startTime)
          this.dataPointQueryVO.endDateTime = this.contactDataAndTime(this.endDate, this.endTime)
          this.dataPointQueryVO.deviceId = this.deviceId
          this.dataPointQueryVO.dataStreamId = props.item.dataStream.dataStreamId
        }
      },
      contactDataAndTime (date, time) {
        let v = new Date(date.substr(0, 4), date.substr(5, 2) - 1, date.substr(8, 2), time.substr(0, 2), time.substr(3, 2))
        // return Math.round(v / 1000)
        return v.getTime()
      },
      selectDataPoints (dataStreamVO) {
        if (dataStreamVO == null) {
          dataStreamVO = this.deviceVO.dataStreamVOs.find(v => v.dataStream.dataStreamId === this.dataPointQueryVO.dataStreamId)
        }
        this.dataPointQueryVO.page = this.pagination.page
        this.dataPointQueryVO.rowsPerPage = this.pagination.rowsPerPage
        let url = '/data_point'
        /* url += qs.stringify(this.dataPointQueryVO, {
          addQueryPrefix: true
        }) */
        let data = JSON.stringify(this.dataPointQueryVO)
        this.$store.commit('setGlobalLoading', true)
        this.$http.post(url, data, {
          headers: {
            'Content-Type': 'application/json;charset=UTF-8'
          }
        }).then(res => {
          if (res.data.code === 0) {
            if (res.data.data !== null && !_.isEmpty(res.data.data)) {
              dataStreamVO.dataPoints = res.data.data
              this.total = dataStreamVO.totalDataPoint
              this.updateCharts(dataStreamVO)
            } else {
              dataStreamVO.dataPoints = []
            }
          }
        }).catch().finally(() => setTimeout(() => this.$store.commit('setGlobalLoading', false), 500))
      },
      updateCharts (dataStreamVO) {
        let code = dataStreamVO.dataStream.dataStreamCode
        this.chartData.columns = [ '时间', code ]
        this.chartData.rows = dataStreamVO.dataPoints.map(value => {
          let ob = {}
          ob['时间'] = new Date(value.dataPointTimestamp)
          ob[code] = value.dataPointData
          return ob
        })
      }
    }
  }
</script>

<style scoped>

</style>
