// http://eslint.org/docs/user-guide/configuring

module.exports = {
  root: true,
  env: {
    browser: true,
  },
  extends: 'vuetify',
  // https://github.com/feross/standard/blob/master/RULES.md#javascript-standard-style
  // add your custom rules here
  'rules': {
    // allow debugger during development
    'no-debugger': process.env.NODE_ENV === 'production' ? 2 : 0,
    'vue/valid-v-on': 'off',
  }
};
