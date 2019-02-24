import Vue from 'vue';
import Router from 'vue-router';

import Home from '@/views/Home';
import Register from '@/views/Register';
import MemberCenter from '@/views/MemberCenter';
import RestaurantCenter from '@/views/RestaurantCenter';
import BookCenter from '@/views/BookCenter';

import MemberInfo from '@/components/member/MemberInfo';
import MemberAddress from '@/components/member/MemberAddress';
import RegisterInfo from '@/components/restaurant/RegisterInfo';
import MarketInfo from '@/components/restaurant/MarketInfo';
import CategoryManager from '@/components/restaurant/CategoryManager';
import GoodsManager from '@/components/restaurant/GoodsManager';
import GoodsEditor from '@/components/restaurant/GoodsEditor';
import ComboManager from '@/components/restaurant/ComboManager';
import ComboEditor from '@/components/restaurant/ComboEditor';
import RestaurantPage from '@/components/book/RestaurantPage';
import GoodsPage from '@/components/book/GoodsPage';

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
      path: '/bookCenter',
      name: 'bookCenter',
      component: BookCenter,
      children: [
        {
          path: '',
          name: 'restaurantPage',
          component: RestaurantPage
        },
        {
          path: '/bookCenter/:id',
          name: 'goodsPage',
          component: GoodsPage
        }
      ]
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
          path: 'category',
          name: 'category',
          component: CategoryManager
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
        },
        {
          path: 'combo',
          name: 'combo',
          component: ComboManager
        },
        {
          path: 'editCombo',
          name: 'editCombo',
          component: ComboEditor,
          props: true
          // props: (route) => ({gid: route.query.gid, aim: route.query.aim})
        }
      ]
    }
  ]
});
