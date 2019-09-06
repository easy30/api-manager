import Vue from 'vue'
import App from './App.vue'
import router from './router'
//npm install vue-cookies --save https://www.npmjs.com/package/vue-cookies
import VueCookies from 'vue-cookies'
import './plugins/element.js'
import utils from './plugins/utils.js'
import global from './global.js'
import VueI18n from 'vue-i18n'
import lang from './lang.js'
import './assets/style.css'
import './assets/el-style.css'
import Page from './components/page.vue'
import DropDown from './components/drop-down.vue'
//import './assets/bootstrap/css/bootstrap.css'
// 引入axios，并加到原型链中
import {axios,ajax} from './ajax'
import VueAxios from 'vue-axios'
//axios.defaults.headers.post["Content-Type"] = "application/x-www-form-urlencoded";
//axios.defaults.headers['Content-Type'] = 'application/x-www-form-urlencoded;charset=UTF-8';

function  showError(error){
    vue.$message({
        showClose: true,
        duration: 15000,
        message: (error.code?error.code+" ":"")+error.message,
        type: 'warning'
    });

}

ajax.error(showError);



Vue.use(VueAxios,axios);
Vue.use(VueI18n) ;
Vue.use(VueCookies);
VueCookies.config('7d');

Vue.component("Page",Page);
Vue.component("DropDown",DropDown);
const i18n = new VueI18n({
    locale: 'cn',    // 语言标识, 通过切换locale的值来实现语言切换,this.$i18n.locale
    messages:  lang
})

Vue.config.productionTip = false
Vue.prototype.utils = utils;
Vue.prototype.$g = global;
Vue.prototype.ajax=ajax;
Vue.prototype.$tt = function (args) {

    var s = "";
    var a=args.split(/\s+/);
    for (var i = 0; i < a.length; i++) {
        if(s.length>0) s+=this.$t("separator");
        s += this.$t(a[i]);
    }
    return s;
};


var vue=new Vue({
    i18n,
  router,
  render: h => h(App)
});
vue.$mount('#app');
