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
      component: MemberCenter,
      children: [
        {
          path: '',
          name: 'member'
        },
        {
          path: 'info',
          name: 'member-info'
        },
        {
          path: 'address',
          name: 'member-address'
        },
        {
          path: 'info',
          name: 'memberCenter-info'
        },
        {
          path: 'orders',
          name: 'member-orders'
        },
        {
          path: 'statistics',
          name: 'member-statistics'
        }
      ]
    }
  ]
});
