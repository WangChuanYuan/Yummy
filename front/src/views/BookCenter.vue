<template>
  <div style="height: 100%">
    <ShoppingCart/>
    <el-container class="section" style="background-color: var(--theme-grey)">
      <el-header style="padding: 0">
        <MemberNav/>
      </el-header>
      <el-main style="padding: 0">
        <router-view/>
      </el-main>
    </el-container>
    <Footer/>
  </div>
</template>

<script>
import MemberNav from '../components/member/MemberNav';
import Footer from '../components/Footer';
import ShoppingCart from '../components/book/cart/ShoppingCart';

export default {
  name: 'BookCenter',
  components: {ShoppingCart, Footer, MemberNav}
};
</script>

<style scoped>

</style>
