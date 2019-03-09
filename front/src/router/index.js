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
import NotFound from '@/views/NotFound';

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
import StatisticViewer from '@/components/manager/StatisticViewer';

Vue.use(Router);

export default new Router({
  routes: [
    {
      path: '/',
      name: 'home',
      component: Home,
      meta: {
        requireAuth: false
      }
    },
    {
      path: '/register',
      name: 'register',
      component: Register,
      meta: {
        requireAuth: false
      }
    },
    {
      path: '/bookCenter',
      component: BookCenter,
      children: [
        {
          path: '',
          name: 'restaurantPage',
          component: RestaurantPage,
          meta: {
            requireAuth: true,
            role: 'MEMBER'
          }
        },
        {
          path: '/bookCenter/:id',
          name: 'goodsPage',
          component: GoodsPage,
          meta: {
            requireAuth: true,
            role: 'MEMBER'
          }
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
          component: OrderConfirmer,
          meta: {
            requireAuth: true,
            role: 'MEMBER'
          }
        },
        {
          path: 'result',
          name: 'orderResult',
          component: OrderResult,
          meta: {
            requireAuth: true,
            role: 'MEMBER'
          },
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
          name: 'memberCenter',
          meta: {
            requireAuth: true,
            role: 'MEMBER'
          }
        },
        {
          path: 'info',
          name: 'member-info',
          component: MemberInfo,
          meta: {
            requireAuth: true,
            role: 'MEMBER'
          }
        },
        {
          path: 'pay',
          name: 'member-pay',
          component: MemberPay,
          meta: {
            requireAuth: true,
            role: 'MEMBER'
          }
        },
        {
          path: 'address',
          name: 'member-address',
          component: MemberAddress,
          meta: {
            requireAuth: true,
            role: 'MEMBER'
          }
        },
        {
          path: 'orders',
          name: 'member-orders',
          component: MemberOrders,
          meta: {
            requireAuth: true,
            role: 'MEMBER'
          }
        },
        {
          path: 'consume',
          name: 'member-consume',
          component: MemberConsume,
          meta: {
            requireAuth: true,
            role: 'MEMBER'
          }
        },
        {
          path: 'usage',
          name: 'member-usage',
          component: MemberUsage,
          meta: {
            requireAuth: true,
            role: 'MEMBER'
          }
        }
      ]
    },
    {
      path: '/restaurantCenter',
      component: RestaurantCenter,
      children: [
        {
          path: '',
          name: 'restaurantCenter',
          meta: {
            requireAuth: true,
            role: 'RESTAURANT'
          }
        },
        {
          path: 'registerInfo',
          name: 'registerInfo',
          component: RegisterInfo,
          meta: {
            requireAuth: true,
            role: 'RESTAURANT'
          }
        },
        {
          path: 'marketInfo',
          name: 'marketInfo',
          component: MarketInfo,
          meta: {
            requireAuth: true,
            role: 'RESTAURANT'
          }
        },
        {
          path: 'category',
          name: 'category',
          component: CategoryManager,
          meta: {
            requireAuth: true,
            role: 'RESTAURANT'
          }
        },
        {
          path: 'goods',
          name: 'goods',
          component: GoodsManager,
          meta: {
            requireAuth: true,
            role: 'RESTAURANT'
          }
        },
        {
          path: 'editGoods',
          name: 'editGoods',
          component: GoodsEditor,
          meta: {
            requireAuth: true,
            role: 'RESTAURANT'
          },
          props: true
          // props: (route) => ({gid: route.query.gid, aim: route.query.aim})
        },
        {
          path: 'combo',
          name: 'combo',
          component: ComboManager,
          meta: {
            requireAuth: true,
            role: 'RESTAURANT'
          }
        },
        {
          path: 'editCombo',
          name: 'editCombo',
          component: ComboEditor,
          meta: {
            requireAuth: true,
            role: 'RESTAURANT'
          },
          props: true
          // props: (route) => ({gid: route.query.gid, aim: route.query.aim})
        },
        {
          path: 'promotion',
          name: 'promotion',
          component: PromotionManager,
          meta: {
            requireAuth: true,
            role: 'RESTAURANT'
          }
        },
        {
          path: 'orders',
          name: 'restaurant-orders',
          component: OrderManager,
          meta: {
            requireAuth: true,
            role: 'RESTAURANT'
          }
        },
        {
          path: 'statistic',
          name: 'restaurant-statistic',
          component: RestaurantStatistic,
          meta: {
            requireAuth: true,
            role: 'RESTAURANT'
          }
        }
      ]
    },
    {
      path: '/manager',
      name: 'manager',
      component: ManagerLogin,
      meta: {
        requireAuth: false
      }
    },
    {
      path: '/managerCenter',
      component: ManagerCenter,
      children: [
        {
          path: '',
          name: 'managerCenter',
          meta: {
            requireAuth: true,
            role: 'MANAGER'
          }
        },
        {
          path: 'registration',
          name: 'manager-registration',
          component: RegistrationChecker,
          meta: {
            requireAuth: true,
            role: 'MANAGER'
          }
        },
        {
          path: 'statistic',
          name: 'manger-statistic',
          component: StatisticViewer,
          meta: {
            requireAuth: true,
            role: 'MANAGER'
          }
        }
      ]
    },
    {
      path: '/404',
      name: '404',
      component: NotFound
    },
    {
      path: '*',
      redirect: '/404'
    }
  ]
});
