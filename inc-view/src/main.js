// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import router from './router'
import store from './vuex/store.js'
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import '@/assets/iconfont.css'
import '@/assets/styles/main.scss'
import '@/assets/css/font-awesome.min.css'
//拖拽画布
import { fabric } from 'fabric';
import 'fabric-customise-controls';//fabric的扩展，用作缩放的图标
//复制到剪贴板
import VueClipboard from 'vue-clipboard2'

Vue.config.productionTip = false
Vue.use(ElementUI)
Vue.use(fabric);
Vue.use(VueClipboard)

/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  store,
  template: '<App/>',
  components: {App}
})

/**权限指令**/
Vue.directive('has', {
  bind: function(el, binding) {
    if (!Vue.prototype.$_has(binding.value)) {
      el.parentNode.removeChild(el);
    }
  }
});
//权限检查方法
Vue.prototype.$_has = function(value) {
  let isExist=false;
  let buttonpermsStr=localStorage.getItem("perms");
  if(buttonpermsStr==undefined || buttonpermsStr==null){
    return false;
  }
  let buttonperms=JSON.parse(buttonpermsStr);
  for(let i=0;i<buttonperms.length;i++){
    if(buttonperms[i].perms.indexOf(value)>-1){
      isExist=true;
      break;
    }
  }
  return isExist;
};

