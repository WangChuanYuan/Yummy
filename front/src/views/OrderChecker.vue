<template>
  <div style="height: 100%">
    <el-container class="section">
      <el-header>
        <MemberNav/>
      </el-header>
      <el-main class="order-checker">
        <el-container>
          <el-header style="height: 120px">
            <div class="clear-fix banner">
              <div class="banner-topic">
                <span style="font-size: 35px">Yummy!</span>
                <span style="font-size: 30px">&nbsp;|&nbsp;</span>
                <span style="font-size: 25px">订单信息</span>
              </div>
              <div class="banner-steps">
                <el-steps align-center>
                  <el-step title="选择商品" status="success"></el-step>
                  <el-step title="确认订单信息" status="success"></el-step>
                  <el-step title="成功提交订单" :status="confirmed ? 'success' : 'wait'"></el-step>
                </el-steps>
              </div>
            </div>
          </el-header>
          <router-view @success="confirm"/>
        </el-container>
      </el-main>
    </el-container>
    <Footer/>
  </div>
</template>

<script>
import MemberNav from '../components/member/MemberNav';
import Footer from '../components/Footer';

export default {
  name: 'OrderChecker',
  components: {Footer, MemberNav},
  data () {
    return {
      confirmed: false
    };
  },
  methods: {
    confirm (bills) {
      this.confirmed = true;
      this.$router.push({
        name: 'orderResult',
        params: {
          bills: bills
        }
      });
    }
  }
};
</script>

<style scoped>
  .el-header {
    padding: 0;
  }

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

  .order-checker {
    min-height: 100%;
    padding: 0;
    background-color: var(--theme-grey);
  }
</style>
