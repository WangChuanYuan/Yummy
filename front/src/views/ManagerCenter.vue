<template>
  <el-container style="min-height: 100%; background-color: var(--theme-grey)">
    <el-aside class="menu">
      <el-menu :default-active="this.$route.name"
               @select="navigation"
               text-color="white"
               active-text-color="var(--theme-golden)"
               style="background: transparent">
        <div style="margin: 30px auto 30px auto">
          <span class="logo-prefix">Yummy!</span>
          <span class="logo-suffix">后台</span>
        </div>
        <el-menu-item index="background">
          <i class="el-icon-info"></i>
          <span>后台管理</span>
        </el-menu-item>
        <el-menu-item index="manager-registration">
            <i class="el-icon-edit-outline"></i>
            <span>门店管理</span>
        </el-menu-item>
        <el-menu-item index="manager-statistic">
          <i class="el-icon-view"></i>
          <span>数据统计</span>
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
import Api from '../assets/js/api';
import {Code} from '../assets/js/attrib';

export default {
  name: 'ManagerCenter',
  methods: {
    navigation (key) {
      switch (key) {
        case 'manager-registration':
          this.$router.push('/managerCenter/registration');
          break;
        case 'manager-statistic':
          this.$router.push('/managerCenter/statistic');
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
        if (data.code === Code.SUCCESS) {
          sessionStorage.clear();
          this.$router.push('/manager');
        }
      });
    }
  }
};
</script>

<style scoped>
  .menu {
    background: url("../assets/image/sea.jpg") top right;
  }

  .logo-prefix {
    color: transparent;
    font-size: 35px;
    text-align: center;
    -webkit-text-stroke: 2px var(--theme-blue);
    letter-spacing: 0.04em;
  }

  .logo-suffix {
    color: transparent;
    font-family: "宋体", "Arial Narrow", HELVETICA, serif;
    font-size: 20px;
    text-align: center;
    -webkit-text-stroke: 1px var(--theme-golden);
    letter-spacing: 0.04em;
  }

  #main {
    margin: 3% auto 0 auto;
    min-height: 80%;
    background-color: white;
  }
</style>
