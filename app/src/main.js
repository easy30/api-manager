import Vue from 'vue'
import App from './App.vue'
import router from './router'
import './plugins/element.js'
import utils from './plugins/utils.js'
import global from './global.js'
import VueI18n from 'vue-i18n'
import lang from './lang.js'
import './assets/style.css'
import './assets/el-style.css'
import Page from './components/page.vue'
//import './assets/bootstrap/css/bootstrap.css'
// 引入axios，并加到原型链中
import axios from 'axios'
import VueAxios from 'vue-axios'

Vue.use(VueAxios,axios);

Vue.use(VueI18n) ;
Vue.component("Page",Page);
const i18n = new VueI18n({
    locale: 'cn',    // 语言标识, 通过切换locale的值来实现语言切换,this.$i18n.locale
    messages:  lang
})

Vue.config.productionTip = false
Vue.prototype.utils = utils;
Vue.prototype.$g = global;


new Vue({
    i18n,
  router,
  render: h => h(App)
}).$mount('#app')
