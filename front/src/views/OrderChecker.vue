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
                <el-steps :active="2" align-center>
                  <el-step title="选择商品" status="success"></el-step>
                  <el-step title="确认订单信息" status="success"></el-step>
                  <el-step title="成功提交订单" status="wait"></el-step>
                </el-steps>
              </div>
            </div>
          </el-header>
          <el-container>
            <el-aside width="665px">
              <div class="order-detail">
                <span style="font-weight: bold">订单详情</span>
                <hr/>
                <div class="order-info">
                  <CartTable v-for="item in carts" :key="item" :rid="item"/>
                </div>
              </div>
            </el-aside>
            <el-main class="member-info">
              <div class="member-info-block">
                <div style="font-weight: bold; padding-bottom: 20px">收货地址</div>
                <AddressBar :class="{'selected-address' : selectedAddress === item.aid}" v-for="item in addresses" :key="item.aid" :address="item" @select="selectAddress"/>
              </div>
              <div class="member-info-block">
                <div style="font-weight: bold; padding-bottom: 20px">备注</div>
                <el-input type="textarea" v-model="tip"></el-input>
              </div>
              <div class="member-info-block">
                <el-button type="success" style="width: 200px">确认下单</el-button>
              </div>
            </el-main>
          </el-container>
        </el-container>
      </el-main>
    </el-container>
    <Footer/>
  </div>
</template>

<script>
import {mapGetters} from 'vuex';
import MemberNav from '../components/member/MemberNav';
import CartTable from '../components/book/cart/CartTable';
import Footer from '../components/Footer';
import AddressBar from '../components/member/address/AddressBar';

export default {
  name: 'OrderChecker',
  components: {AddressBar, Footer, CartTable, MemberNav},
  data () {
    return {
      addresses: [{}, {}],
      selectedAddress: -1,
      tip: ''
    };
  },
  computed: {
    ...mapGetters({
      'carts': 'cart/carts'
    })
  },
  mounted () {
    let sessionCart = this.$store.state.cart.cart;
    let carts = [];
    for (let rid in sessionCart) {
      let cart = {
        rid: rid,
        goods: this.$store.getters['cart/cartGoods'](rid),
        combos: this.$store.getters['cart/cartCombos'](rid)
      };
      carts.push(cart);
    }
  },
  methods: {
    selectAddress (aid) {
      this.selectedAddress = aid;
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

  .order-detail {
    margin-left: 250px;
    background-color: white;
  }

  .order-info {
    padding: 10px 20px 70px 20px;
  }

  .selected-address {
    border: 1px solid var(--theme-blue);
  }

  .member-info {
    padding: 0 0 150px 30px;
    margin: 0 150px 20px 30px;
    background-color: white;
    text-align: left
  }

  .member-info-block {
    padding-top: 20px;
    padding-bottom: 5px;
  }
</style>
