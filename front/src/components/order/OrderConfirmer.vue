<template>
  <el-container>
    <el-aside width="665px">
      <div class="order-detail">
        <div style="padding-top:15px; font-weight: bold">订单详情</div>
        <hr/>
        <div class="order-info">
          <CartTable v-for="item in carts" :key="item" :rid="item"/>
        </div>
      </div>
    </el-aside>
    <el-main class="member-info">
      <div class="member-info-block">
        <div style="font-weight: bold; padding-bottom: 20px">收货地址</div>
        <el-button type="text" @click="$router.push('/memberCenter/address')">管理地址</el-button>
        <AddressBar
          class="to-select"
          :class="{'selected' : address === item.aid}"
          v-for="item in addresses" :key="item.aid" :address="item"
          @select="selectAddress"/>
      </div>
      <div class="member-info-block">
        <div style="font-weight: bold; padding-bottom: 20px">支付方式</div>
        <el-button type="text" @click="$router.push('/memberCenter/pay')">管理支付</el-button>
        <BankCard
          aim="display"
          class="to-select"
          :class="{'selected' : bankcard === item.cardNo}"
          v-for="item in bankcards" :key="item.cardNo" :bankcard="item"
          @select="selectBankCard"/>
      </div>
      <div class="member-info-block">
        <div style="font-weight: bold; padding-bottom: 20px">送达时间</div>
        <el-time-picker
          v-model="arrivalTime"
          value-format="HH:mm:ss"
          :picker-options="{selectableRange: '00:00:00 - 23:59:59'}"
          placeholder="选择送达时间">
        </el-time-picker>
      </div>
      <div class="member-info-block">
        <div style="font-weight: bold; padding-bottom: 20px">备注</div>
        <el-input type="textarea" v-model="tip"></el-input>
      </div>
      <div class="member-info-block">
        <el-button type="success" style="width: 200px" @click="submitOrder">确认下单</el-button>
      </div>
    </el-main>
  </el-container>
</template>

<script>
import Api from '../../assets/js/api';
import {mapGetters, mapActions} from 'vuex';
import {Code} from '../../assets/js/attrib';
import CartTable from '../../components/book/cart/CartTable';
import AddressBar from '../../components/member/address/AddressBar';
import BankCard from '../../components/member/pay/BankCard';

export default {
  name: 'OrderConfirmer',
  components: {BankCard, AddressBar, CartTable},
  data () {
    return {
      addresses: [],
      bankcards: [],
      /* form */
      address: -1,
      bankcard: '',
      arrivalTime: null,
      tip: ''
    };
  },
  computed: {
    ...mapGetters({
      'carts': 'cart/carts'
    })
  },
  mounted () {
    Api.get('/get_addresses', {id: sessionStorage.getItem('id')}).then((data) => {
      if (data) this.addresses = data;
    }).catch(() => {});
    Api.get('/get_bankcards', {mid: sessionStorage.getItem('id')}).then((data) => {
      if (data) this.bankcards = data;
    }).catch(() => {});
  },
  methods: {
    ...mapActions({
      'clearCart': 'cart/clear_cart'
    }),
    selectAddress (aid) {
      this.address = aid;
    },
    selectBankCard (cardNo) {
      this.bankcard = cardNo;
    },
    submitOrder () {
      if (this.address === -1) {
        this.$message.warning('请选择收货地址');
        return;
      }
      if (this.bankcard === '') {
        this.$message.warning('请选择支付方式');
        return;
      }
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
      if (carts.length === 0) {
        this.$message.warning('购物车为空');
        return;
      }
      Api.post('/submit_orders', {
        member: sessionStorage.getItem('id'),
        address: this.address,
        bankcard: this.bankcard,
        arrivalTime: this.arrivalTime,
        tip: this.tip,
        carts: JSON.stringify(carts)
      }).then((data) => {
        if (data.code === Code.SUCCESS) {
          this.clearCart();
          this.$message.success(data.msg);
          this.$emit('success', data.value);
        } else this.$message.warning(data.msg);
      }).catch(() => {});
    }
  }
};
</script>

<style scoped>
  .order-detail {
    margin-left: 250px;
    background-color: white;
  }

  .order-info {
    padding: 10px 20px 70px 20px;
  }

  .to-select:hover, .selected {
    cursor: pointer;
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
