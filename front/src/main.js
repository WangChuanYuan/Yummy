// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue';
import App from './App';
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import VueCookies from 'vue-cookies';
import vRegion from 'v-region';
import BaiduMap from 'vue-baidu-map';
import './assets/css/normal.css';
import './assets/js/extend';
import router from './router';
import store from './store';

Vue.config.productionTip = false;

router.beforeEach((to, from, next) => {
  if (to.meta.requireAuth) {
    let userJson = sessionStorage.getItem('user');
    let isValid = false;
    if (userJson) {
      let user = JSON.parse(userJson);
      isValid = (user.role === to.meta.role);
    }
    if (isValid) {
      next();
    } else {
      if (to.meta.role === 'MANAGER') {
        next({
          path: '/manager'
        });
      } else {
        next({
          path: '/'
        });
      }
    }
  } else next();
});

Vue.use(ElementUI);
Vue.use(VueCookies);
Vue.use(vRegion);
Vue.use(BaiduMap, {
  ak: '1cRLwrvQfqCVZOzf7XPtUgmHmIXI199y'
});

/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  store,
  components: {App},
  template: '<App/>'
});
