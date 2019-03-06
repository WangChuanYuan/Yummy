<template>
  <el-menu class="clear-fix"
           :default-active="this.$route.name"
           @select="navigation"
           mode="horizontal"
           background-color="var(--theme-blue)"
           text-color="#ffffff"
           active-text-color="var(--theme-golden)">
    <el-menu-item index="logo" style="margin-left: 10%; margin-right: 10%">
      <Logo style="-webkit-text-stroke: 1px #ffffff; font-size: 25px;"/>
    </el-menu-item>
    <el-menu-item index="bookCenter">订餐中心</el-menu-item>
    <el-menu-item index="memberCenter">会员中心</el-menu-item>
    <el-menu-item index="register">我要开店</el-menu-item>
    <el-submenu index="member" v-if="id" style="float: right">
      <template slot="title">{{id}}</template>
      <el-menu-item index="member-info">
        <i class="el-icon-info"></i>
        个人信息
      </el-menu-item>
      <el-menu-item index="member-orders">
        <i class="el-icon-tickets"></i>
        我的订单
      </el-menu-item>
      <el-menu-item index="member-address">
        <i class="el-icon-location-outline"></i>
        我的地址
      </el-menu-item>
      <el-menu-item index="member-logout">
        <i class="el-icon-circle-close-outline"></i>
        登出
      </el-menu-item>
      <el-menu-item index="member-evict">
        <i class="el-icon-circle-close"></i>
        注销
      </el-menu-item>
    </el-submenu>
    <el-submenu index="default" v-else style="float: right">
      <template slot="title">点击登录</template>
      <el-menu-item index="default-login">登录</el-menu-item>
      <el-menu-item index="default-register">注册</el-menu-item>
    </el-submenu>
  </el-menu>
</template>

<script>
import Logo from '../Logo';
import Api from '../../assets/js/api';
import {Code} from '../../assets/js/attrib';

export default {
  name: 'MemberNav',
  components: {Logo},
  data () {
    return {
      id: sessionStorage.getItem('id')
    };
  },
  methods: {
    navigation (key) {
      switch (key) {
        case 'bookCenter':
          this.$router.push('/bookCenter');
          break;
        case 'memberCenter':
          this.$router.push('/memberCenter/info');
          break;
        case 'register':
          this.$router.push('/register');
          break;
        case 'member-info':
          this.$router.push('/memberCenter/info');
          break;
        case 'member-orders':
          this.$router.push('/memberCenter/orders');
          break;
        case 'member-address':
          this.$router.push('/memberCenter/address');
          break;
        case 'member-logout':
          this.logout();
          break;
        case 'member-evict':
          this.evict();
          break;
        case 'default-login':
          this.$router.push('/');
          break;
        case 'default-register':
          this.$router.push('/register');
          break;
        default:
          break;
      }
    },
    logout () {
      Api.post('/logout').then((data) => {
        if (data.code === Code.SUCCESS) {
          sessionStorage.clear();
          this.$router.push('/');
        }
      });
    },
    evict () {
      this.$confirm('注销操作无法撤销，是否继续？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        let _this = this;
        Api.post('/evict').then((data) => {
          if (data.code === Code.SUCCESS) {
            sessionStorage.clear();
            _this.$message.success('注销成功');
            _this.$router.push('/');
          } else {
            _this.$message.warning('注销失败');
          }
        });
      }).catch(() => {
        this.$message.info('注销操作已取消');
      });
    }
  }
};
</script>

<style scoped>

</style>
