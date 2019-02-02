import Vue from 'vue';
import Router from 'vue-router';
import Home from '@/views/Home';
import Register from '@/views/Register';
import MemberCenter from '@/views/MemberCenter';

Vue.use(Router);

export default new Router({
  routes: [
    {
      path: '/',
      name: 'home',
      component: Home
    },
    {
      path: '/register',
      name: 'register',
      component: Register
    },
    {
      path: '/memberCenter',
      name: 'memberCenter',
      component: MemberCenter
    }
  ]
});
