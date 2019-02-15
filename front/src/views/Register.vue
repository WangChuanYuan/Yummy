<template>
  <div style="height: 100%">
    <div class="section">
      <Logo :class="[isRestaurant ? 'logoA' : 'logoB']"/>
      <RestaurantRegister v-if="isRestaurant"/>
      <MemberRegister v-else/>
      <el-checkbox v-model="isRestaurant">我要开店</el-checkbox>
    </div>
    <Footer/>
  </div>
</template>

<script>
import Logo from '../components/Logo';
import Footer from '../components/Footer';
import RestaurantRegister from '../components/restaurant/RegisterForm';
import MemberRegister from '../components/member/RegisterForm';

export default {
  name: 'Register',
  components: {MemberRegister, RestaurantRegister, Footer, Logo},
  data () {
    return {
      isRestaurant: false
    };
  }
};
</script>

<style scoped>
  .logoA {
    padding-top: 2%;
  }

  .logoB {
    padding-top: 5%;
  }
</style>
