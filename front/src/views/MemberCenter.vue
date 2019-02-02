<template>
  <div style="height: 100%">
    <Navigation/>
    <el-container style="background-color: var(--theme-grey); height: 100%">
      <el-aside width="20%">
        <el-menu :default-active="this.$route.path" :router="true" background-color="var(--theme-grey)" active-text-color="var(--theme-blue)">
          <el-menu-item index="/memberCenter">
            <i class="el-icon-info"></i>
            个人中心
          </el-menu-item>
          <el-submenu index="/memberCenter">
            <template slot="title">
              <i class="el-icon-edit-outline"></i>
              <span>我的资料</span>
            </template>
            <el-menu-item index="/memberCenter/info">个人资料</el-menu-item>
            <el-menu-item index="/memberCenter/address">地址管理</el-menu-item>
          </el-submenu>
          <el-menu-item index="/memberCenter/orders">
            <i class="el-icon-tickets"></i>
            我的订单
          </el-menu-item>
          <el-menu-item index="/memberCenter/statistics">
            <i class="el-icon-news"></i>
            统计信息
          </el-menu-item>
        </el-menu>
      </el-aside>
      <el-main>
        <router-view/>
      </el-main>
    </el-container>
    <Footer/>
  </div>
</template>

<script>
import Navigation from '../components/Navigation';
import Footer from '../components/Footer';

export default {
  name: 'MemberCenter',
  components: {Footer, Navigation}
};
</script>

<style scoped>

</style>
