import Vue from 'vue';
import Router from 'vue-router';

import Home from '@/views/Home';
import Register from '@/views/Register';
import MemberCenter from '@/views/MemberCenter';
import RestaurantCenter from '@/views/RestaurantCenter';

import MemberInfo from '@/components/member/MemberInfo';
import MemberAddress from '@/components/member/MemberAddress';
import RegisterInfo from '@/components/restaurant/RegisterInfo';
import MarketInfo from '@/components/restaurant/MarketInfo';
import GoodsManager from '@/components/restaurant/GoodsManager';
import GoodsEditor from '@/components/restaurant/GoodsEditor';

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
          name: 'member-info',
          component: MemberInfo
        },
        {
          path: 'address',
          name: 'member-address',
          component: MemberAddress
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
    },
    {
      path: '/restaurantCenter',
      name: 'restaurantCenter',
      component: RestaurantCenter,
      children: [
        {
          path: '',
          name: 'restaurant'
        },
        {
          path: 'registerInfo',
          name: 'registerInfo',
          component: RegisterInfo
        },
        {
          path: 'marketInfo',
          name: 'marketInfo',
          component: MarketInfo
        },
        {
          path: 'goods',
          name: 'goods',
          component: GoodsManager
        },
        {
          path: 'editGoods',
          name: 'editGoods',
          component: GoodsEditor,
          props: true
          // props: (route) => ({gid: route.query.gid, aim: route.query.aim})
        }
      ]
    }
  ]
});
