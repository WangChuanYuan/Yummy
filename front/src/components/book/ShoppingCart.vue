<template>
  <div class="side-bar-wrapper" :class="{hidden: isHidden}">
    <div class="side-bar-nav">
      <div class="side-bar-nav-bt" @click="isHidden = false">
        <div class="el-icon-goods"></div>
        <span>购物车</span>
      </div>
    </div>
    <div class="side-bar-ct">
      <div class="side-bar-ct-top" @click="isHidden = true">
        <span>购物车</span>
        <div class="el-icon-d-arrow-right"></div>
      </div>
      <div class="side-bar-ct-main">
        <CartTable v-for="item in carts" :key="item" :rid="item"/>
      </div>
      <div class="side-bar-ct-bottom">
        <span>总价:{{total}}</span>
        <div>
          <el-button type="primary" style="height: 30px; width: 220px" @click="pay" v-show="total > 0">去结算</el-button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import {mapGetters} from 'vuex';
import CartTable from './CartTable';

export default {
  name: 'ShoppingCart',
  components: {CartTable},
  data () {
    return {
      isHidden: true
    };
  },
  computed: {
    ...mapGetters({
      'carts': 'cart/carts',
      'total': 'cart/cartMoney'
    })
  },
  methods: {
    pay () {
      this.$router.push('/orderChecker');
    }
  }
};
</script>

<style scoped>
  .hidden {
    transform: translate(310px, 0);
    transition: all 1s;
  }

  .side-bar-wrapper {
    height: 100%;
    width: 340px;
    position: fixed;
    z-index: 1;
    top: 0;
    right: 0;
    overflow-x: hidden;
  }

  .side-bar-nav {
    height: 100%;
    width: 30px;
    background-color: var(--theme-deep-grey);
  }

  .side-bar-nav-bt {
    width: 30px;
    position: fixed;
    top: 35%;
    color: white;
  }

  .side-bar-nav-bt span {
    writing-mode: vertical-lr;
    text-align: center;
  }

  .side-bar-nav-bt:hover {
    background-color: var(--theme-blue);
  }

  .side-bar-ct {
    min-height: 100%;
    width: 310px;
    position: fixed;
    top: 0;
    right: 0;
    background-color: var(--theme-grey);
  }

  .side-bar-ct-top {
    height: 30px;
    background-color: white;
  }

  .side-bar-ct-top:hover {
    transform: scale(1.2);
    transition: all 1s;
  }

  .side-bar-ct-main {
    width: 310px;
    position: fixed;
    top: 30px;
    right: 0;
  }

  .side-bar-ct-bottom {
    height: 60px;
    width: 310px;
    position: fixed;
    bottom: 0;
    right: 0;
    color: white;
    background-color: var(--theme-medium-grey);
  }
</style>
