<template>
  <el-container style="min-height: 100%; background-color: var(--theme-grey)">
    <el-aside style="background-color: var(--theme-deep-grey)">
      <el-menu :default-active="this.$route.name"
               @select="navigation"
               background-color="var(--theme-deep-grey)"
               text-color="white"
               active-text-color="var(--theme-golden)">
        <Logo style="-webkit-text-stroke: 1px var(--theme-golden); font-size: 35px; margin: 30px auto 30px auto"/>
        <el-menu-item index="restaurantCenter">
          <i class="el-icon-info"></i>
          <span>门店中心</span>
        </el-menu-item>
        <el-submenu index="info">
          <template slot="title">
            <i class="el-icon-edit-outline"></i>
            <span>门店管理</span>
          </template>
          <el-menu-item-group>
            <el-menu-item index="registerInfo">注册信息</el-menu-item>
            <el-menu-item index="marketInfo">营销信息</el-menu-item>
          </el-menu-item-group>
        </el-submenu>
        <el-submenu index="goods">
          <template slot="title">
            <i class="el-icon-goods"></i>
            <span>菜单管理</span>
          </template>
          <el-menu-item-group>
            <el-menu-item index="goods">单品管理</el-menu-item>
            <el-menu-item index="combo">套餐管理</el-menu-item>
          </el-menu-item-group>
        </el-submenu>
        <el-menu-item index="orders">
          <i class="el-icon-tickets"></i>
          <span>订单管理</span>
        </el-menu-item>
        <el-menu-item index="statistics">
          <i class="el-icon-news"></i>
          <span>统计信息</span>
        </el-menu-item>
        <el-menu-item index="logout">
          <i class="el-icon-circle-close-outline"></i>
          <span>退出登录</span>
        </el-menu-item>
      </el-menu>
    </el-aside>
    <el-main>
      <router-view id="main"></router-view>
    </el-main>
  </el-container>
</template>

<script>
import Logo from '../components/Logo';
import Api from '../assets/js/api';

export default {
  name: 'RestaurantCenter',
  components: {Logo},
  data () {
    return {};
  },
  methods: {
    navigation (key) {
      switch (key) {
        case 'restaurantCenter':
          this.$router.push('/restaurantCenter');
          break;
        case 'registerInfo':
          this.$router.push('/restaurantCenter/registerInfo');
          break;
        case 'marketInfo':
          this.$router.push('/restaurantCenter/marketInfo');
          break;
        case 'goods':
          this.$router.push('/restaurantCenter/goods');
          break;
        case 'combo':
          this.$router.push('/restaurantCenter/combo');
          break;
        case 'orders':
          break;
        case 'statistics':
          break;
        case 'logout':
          this.logout();
          break;
        default:
          break;
      }
    },
    logout () {
      Api.post('/logout').then((data) => {
        if (data.code === 'SUCCESS') {
          sessionStorage.clear();
          this.$router.push('/');
        }
      });
    }
  }
};
</script>

<style scoped>
  #main {
    margin: 3% auto 0 auto;
    min-height: 90%;
    min-width: 80%;
    background-color: white;
  }
</style>
