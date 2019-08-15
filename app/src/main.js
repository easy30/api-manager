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
import qs from 'qs'

//axios.defaults.headers.post["Content-Type"] = "application/x-www-form-urlencoded";
//axios.defaults.headers['Content-Type'] = 'application/x-www-form-urlencoded;charset=UTF-8';
axios.$post=function(url,data,succesCallback){
    console.log(url);
	this.post(url,  qs.stringify(data))
	.then( (response)=>{succesCallback(response);})
        .catch((response) => {
		alert(response);
	})
}
axios.$get=function(url,succesCallback){
    console.log(url);
    this.get(url)
        .then( (response)=>{succesCallback(response);})
        .catch((response) => {
            alert(response);
        })
}



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
Vue.prototype.$tt = function (args) {

    var s = "";
    var a=args.split(/\s+/);
    for (var i = 0; i < a.length; i++) {
        if(s.length>0) s+=this.$t("separator");
        s += this.$t(a[i]);
    }
    return s;
};


new Vue({
    i18n,
  router,
  render: h => h(App)
}).$mount('#app')
