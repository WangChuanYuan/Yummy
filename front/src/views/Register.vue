<template>
  <el-container>
    <el-header style="margin-top: 5%">
      <Logo/>
    </el-header>
    <el-main>
      <RestaurantInfo v-if="isRestaurant"/>
      <MemberRegister v-else/>
      <el-checkbox v-model="isRestaurant">我要开店</el-checkbox>
    </el-main>
    <el-footer style="padding: 0">
      <Footer/>
    </el-footer>
  </el-container>
</template>

<script>
import Logo from '../components/Logo';
import Footer from '../components/Footer';
import RestaurantInfo from '../components/restaurant/RestaurantInfo';
import MemberRegister from '../components/member/MemberRegister';

export default {
  name: 'Register',
  components: {MemberRegister, RestaurantInfo, Footer, Logo},
  data () {
    return {
      isRestaurant: false
    };
  }
};
</script>

<style scoped>

</style>
