import Vue from 'vue';
import Router from 'vue-router';

import Home from '@/views/Home';
import Register from '@/views/Register';
import MemberCenter from '@/views/MemberCenter';
import RestaurantCenter from '@/views/RestaurantCenter';
import BookCenter from '@/views/BookCenter';
import OrderChecker from '@/views/OrderChecker';
import ManagerLogin from '@/views/ManagerLogin';
import ManagerCenter from '@/views/ManagerCenter';

import MemberInfo from '@/components/member/MemberInfo';
import MemberAddress from '@/components/member/MemberAddress';
import MemberOrders from '@/components/member/MemberOrders';
import MemberPay from '@/components/member/MemberPay';
import MemberConsume from '@/components/member/statistic/MemberConsume';
import MemberUsage from '@/components/member/statistic/MemberUsage';
import RestaurantPage from '@/components/book/RestaurantPage';
import GoodsPage from '@/components/book/GoodsPage';
import OrderConfirmer from '@/components/order/OrderConfirmer';
import OrderResult from '@/components/order/OrderResult';
import RegisterInfo from '@/components/restaurant/RegisterInfo';
import MarketInfo from '@/components/restaurant/MarketInfo';
import CategoryManager from '@/components/restaurant/CategoryManager';
import GoodsManager from '@/components/restaurant/GoodsManager';
import GoodsEditor from '@/components/restaurant/GoodsEditor';
import ComboManager from '@/components/restaurant/ComboManager';
import ComboEditor from '@/components/restaurant/ComboEditor';
import PromotionManager from '@/components/restaurant/PromotionManager';
import OrderManager from '@/components/restaurant/OrderManager';
import RestaurantStatistic from '@/components/restaurant/RestaurantStatistic';
import RegistrationChecker from '@/components/manager/RegistrationChecker';

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
      path: '/orderChecker',
      component: OrderChecker,
      children: [
        {
          path: '',
          name: 'orderChecker',
          component: OrderConfirmer
        },
        {
          path: 'result',
          name: 'orderResult',
          component: OrderResult,
          props: true
        }
      ]
    },
    {
      path: '/memberCenter',
      component: MemberCenter,
      children: [
        {
          path: '',
          name: 'memberCenter'
        },
        {
          path: 'info',
          name: 'member-info',
          component: MemberInfo
        },
        {
          path: 'pay',
          name: 'member-pay',
          component: MemberPay
        },
        {
          path: 'address',
          name: 'member-address',
          component: MemberAddress
        },
        {
          path: 'orders',
          name: 'member-orders',
          component: MemberOrders
        },
        {
          path: 'consume',
          name: 'member-consume',
          component: MemberConsume
        },
        {
          path: 'usage',
          name: 'member-usage',
          component: MemberUsage
        }
      ]
    },
    {
      path: '/restaurantCenter',
      component: RestaurantCenter,
      children: [
        {
          path: '',
          name: 'restaurantCenter'
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
        },
        {
          path: 'promotion',
          name: 'promotion',
          component: PromotionManager
        },
        {
          path: 'orders',
          name: 'orders',
          component: OrderManager
        },
        {
          path: 'statistic',
          name: 'statistic',
          component: RestaurantStatistic
        }
      ]
    },
    {
      path: '/manager',
      name: 'manager',
      component: ManagerLogin
    },
    {
      path: '/managerCenter',
      component: ManagerCenter,
      children: [
        {
          path: '',
          name: 'managerCenter'
        },
        {
          path: 'registrations',
          name: 'registrations',
          component: RegistrationChecker
        }
      ]
    }
  ]
});
