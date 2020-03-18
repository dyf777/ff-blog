import Vue from 'vue'
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import App from './App.vue'
import router from './router'
import store from './store'
//导入请求方法
import {postKeyValueRequest} from "./utils/api";
import {getRequest} from "./utils/api";
import {postRequest} from "./utils/api";
import {putRequest} from "./utils/api";
import {deleteRequest} from "./utils/api";
//全局注册请求方法
Vue.prototype.postKeyValueRequest=postKeyValueRequest;
Vue.prototype.getRequest=postKeyValueRequest;
Vue.prototype.postRequest=postKeyValueRequest;
Vue.prototype.deleteRequest=postKeyValueRequest;
Vue.prototype.putRequest=postKeyValueRequest;

Vue.use(ElementUI);

Vue.config.productionTip = false

new Vue({
  store,
  router,
  render: h => h(App)
}).$mount('#app')
