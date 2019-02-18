// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue';
import App from './App';
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import './assets/css/normal.css';
import router from './router';
import VueCookies from 'vue-cookies';
import vRegion from 'v-region';
import './assets/js/extend';

Vue.config.productionTip = false;

Vue.use(ElementUI);
Vue.use(VueCookies);
Vue.use(vRegion);

/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  components: {App},
  template: '<App/>'
});
