<template>
  <el-container style="height: 100%">
    <el-header style="padding: 0">
      <MemberNav/>
    </el-header>
    <el-main class="order-info">
      <div class="clear-fix banner">
        <div class="banner-topic">
          <span style="font-size: 35px">Yummy!</span>
          <span style="font-size: 30px">&nbsp;|&nbsp;</span>
          <span style="font-size: 25px">订单信息</span>
        </div>
        <div class="banner-steps">
          <el-steps :active="2" align-center>
            <el-step title="选择商品" status="success"></el-step>
            <el-step title="确认订单信息" status="success"></el-step>
            <el-step title="成功提交订单" status="wait"></el-step>
          </el-steps>
        </div>
      </div>
    </el-main>
  </el-container>
</template>

<script>
import MemberNav from '../components/member/MemberNav';
import Logo from '../components/Logo';

export default {
  name: 'OrderChecker',
  components: {Logo, MemberNav}
};
</script>

<style scoped>
  .banner {
    height: 100px;
    width: 100%;
    background: url('../assets/image/peanuts.jpg') no-repeat left;
    background-size: cover;
    color: white;
  }

  .banner .banner-topic {
    float: left;
    height: 100%;
    margin-top: 25px;
    margin-left: 200px;
  }

  .banner .banner-steps {
    width: 500px;
    float: right;
    margin-top: 30px;
    margin-right: 150px;
  }

  .order-info {
    min-height: 100%;
    padding: 0;
    background-color: var(--theme-grey);
  }
</style>
