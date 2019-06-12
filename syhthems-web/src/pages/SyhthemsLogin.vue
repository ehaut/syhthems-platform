<template>
  <v-layout
    align-center
    justify-center
    row
    wrap
  >
    <span
      v-if="loading"
      class="inline-box"
    />
    <v-flex
      v-if="loading"
      xs2
      md2
      sm2
    >
      <v-progress-circular
        indeterminate
      />
      <p class="body-1">
        登录中...
      </p>
    </v-flex>
    <v-flex
      v-show="!loading"
      md4
    >
      <v-card>
        <v-card-title primary-title>
          <h5 class="headline">
            {{ msg }}
          </h5>
        </v-card-title>
        <v-card-text v-if="error">
          <p class="body-1 red--text">
            {{ error }}
          </p>
        </v-card-text>

        <v-card-actions>
          <v-btn
            v-show="loginUrl"
            color="info"
            :href="loginUrl"
          >
            点击登录
          </v-btn>
        </v-card-actions>
      </v-card>
    </v-flex>
  </v-layout>
</template>

<script>
  export default {
    name: 'SyhthemsLogin',
    data: function () {
      return {
        loading: false,
        redirectFrom: '',
        loginUrl: '',
        error: '',
        msg: '请点击下方按钮登录',
        buttonMsg: '点击登录'
      }
    },
    computed: {
      isAuthenticated () {
        return this.$store.getters['oauth2/isAuthenticated']
      }
    },
    created () {
      //
      console.log('Login created...')
    },
    mounted () {
      console.log('Login mounted...')
      if (this.$route.query.code) {
        this.loading = true
        this.$oauth2.doAccessTokenRequest(this.$route.query)
          .then((success) => {
            this.msg = '登录成功，正在为您跳转...'
            if (this.redirectFrom) {
              window.location.href = window.location.origin + this.redirectFrom.fullPath
            } else {
              window.location.href = window.location.origin + '/'
            }
          })
          .catch(error => {
            this.error = error.toString()
            this.msg = '获取token失败，请点击下方按钮重新登录'
            this.loginUrl = this.$oauth2.getLoginUrl()
          })
          .finally(() => {
            this.loading = false
          })
      } else if (this.$route.query.refreshToken) {
        if (this.$oauth2.isExpired() && !this.$oauth2.isRefreshTokenExpired()) {
          this.loading = true
          this.$oauth2.doRefreshTokenRequest()
            .then(value => {
              this.msg = '登录成功，正在为您跳转...'
              if (this.redirectFrom) {
                window.location.href = window.location.origin + this.redirectFrom.fullPath
              } else {
                window.location.href = window.location.origin + '/'
              }
            })
            .catch(reason => {
              this.error = reason.toString()
              this.msg = '刷新token失败，请刷新该页面重试'
            })
            .finally(() => {
              this.loading = false
            })
        }
      } else if (this.$oauth2.isAuthenticated()) {
        this.msg = '你已登录'
        if (this.redirectFrom) {
          window.location.href = window.location.origin + this.redirectFrom.fullPath
        } else {
          window.location.href = window.location.origin + '/'
        }
      } else {
        this.loginUrl = this.$oauth2.getLoginUrl()
      }
    },
    methods: {
      //
    },
    beforeRouteUpdate (to, from, next) {
      // 在当前路由改变，但是该组件被复用时调用
      // 举例来说，对于一个带有动态参数的路径 /foo/:id，在 /foo/1 和 /foo/2 之间跳转的时候，
      // 由于会渲染同样的 Foo 组件，因此组件实例会被复用。而这个钩子就会在这个情况下被调用。
      // 可以访问组件实例 `this`
      console.log('Login beforeRouterUpdate...')
    },

    beforeRouteEnter (to, from, next) {
      next(vm => {
        // 通过 `vm` 访问组件实例
        vm.redirectFrom = from
        console.log('Login beforeRouteEnter 的回调函数执行了... redirectFrom: ' + vm.redirectFrom.fullPath)
      })
    }
  }
</script>

<style scoped>
  .inline-box {
    z-index: 999;
    filter: alpha(opacity=50);
    background: #666;
    opacity: 0.5;
    -moz-opacity: 0.5;
    left: 0;
    top: 0;
    height: 100%;
    width: 100%;
    position: fixed;
  }
</style>
