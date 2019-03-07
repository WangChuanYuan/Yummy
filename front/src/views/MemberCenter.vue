<template>
  <div style="height: 100%">
    <el-container class="section" style="background-color: var(--theme-grey)">
      <el-header style="padding: 0">
        <MemberNav/>
      </el-header>
      <el-container>
        <el-aside>
          <el-menu :default-active="this.$route.path"
                   :router="true"
                   background-color="var(--theme-grey)"
                   active-text-color="var(--theme-blue)">
            <el-menu-item index="/memberCenter/info">
              <i class="el-icon-info"></i>
              <span>个人中心</span>
            </el-menu-item>
            <el-submenu index="/memberCenter">
              <template slot="title">
                <i class="el-icon-edit-outline"></i>
                <span>我的资料</span>
              </template>
              <el-menu-item index="/memberCenter/info">个人资料</el-menu-item>
              <el-menu-item index="/memberCenter/address">地址管理</el-menu-item>
              <el-menu-item index="/memberCenter/pay">支付管理</el-menu-item>
            </el-submenu>
            <el-menu-item index="/memberCenter/orders">
              <i class="el-icon-tickets"></i>
              <span>我的订单</span>
            </el-menu-item>
            <el-submenu index="/memberCenter/statistic">
              <template slot="title">
                <i class="el-icon-news"></i>
                <span>统计信息</span>
              </template>
              <el-menu-item index="/memberCenter/consume">消费统计</el-menu-item>
              <el-menu-item index="/memberCenter/usage">订单统计</el-menu-item>
            </el-submenu>
          </el-menu>
        </el-aside>
        <el-main>
          <router-view id="main"/>
        </el-main>
      </el-container>
    </el-container>
    <Footer/>
  </div>
</template>

<script>
import MemberNav from '../components/member/MemberNav';
import Footer from '../components/Footer';

export default {
  name: 'MemberCenter',
  components: {Footer, MemberNav}
};
</script>

<style scoped>
  #main {
    text-align: left;
    padding: 5px 5px 5px 20px;
    min-height: 90%;
    width: 80%;
    background-color: white;
  }
</style>
