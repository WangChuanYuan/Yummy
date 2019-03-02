<template>
  <el-card class="box-card clear-fix" :body-style="{ padding: '0px' }">
    <div class="clear-fix">
      <div class="avatar">
        <img :src="inGoods.avatar">
      </div>
      <div class="info">
        <span style="font-size: 20px; font-weight: bold">{{inGoods.name}}</span>
        <br/>
        <div style="font-size: 13px">
          <span>类别:</span>
          <span>{{inGoods.category}}</span>
        </div>
        <div style="font-size: 13px">
          <span>价格:</span>
          <span>{{inGoods.price}}</span>
        </div>
        <br/>
        <div class="omission" style="font-size: 13px">
          <span>每日供应:</span>
          <span>{{inGoods.dailySupply}}</span>
          <br/>
          <span>库存总量:</span>
          <span>{{inGoods.stock}}</span>
        </div>
      </div>
    </div>
    <div class="goods-op" v-show="aim === 'manage'">
      <el-button type="text" @click="modifyGoods">修改</el-button>
      <el-button type="text" @click="deleteGoods">下架</el-button>
    </div>
    <div class="goods-op" v-show="aim === 'purchase'">
      <el-input-number :min="1" :precision="0" size="mini" v-model="inGoods.num"></el-input-number>
      <el-button type="text" @click="addToCart">加入购物车</el-button>
    </div>
  </el-card>
</template>

<script>
import {mapGetters, mapActions} from 'vuex';

export default {
  name: 'GoodsCard',
  props: {
    'aim': {
      // manage || purchase
      type: String,
      default: 'manage'
    },
    'goods': {
      type: Object,
      default: function () {
        return {
          gid: 0,
          cgid: 0,
          category: '',
          num: 1,
          avatar: require('../../assets/image/oil.jpg'),
          name: '',
          description: '',
          price: 0,
          dLeft: 0,
          stock: 0,
          dailySupply: 0,
          startDate: '',
          endDate: ''
        };
      }
    }
  },
  data () {
    return {
      inGoods: JSON.parse(JSON.stringify(this.goods))
    };
  },
  watch: {
    goods (val) {
      this.inGoods = JSON.parse(JSON.stringify(val));
    }
  },
  computed: {
    ...mapGetters({
      hasGoods: 'cart/hasGoods'
    })
  },
  methods: {
    modifyGoods () {
      this.$router.push({
        name: 'editGoods',
        params: {
          gid: this.inGoods.gid,
          aim: 'modify'
        }
      });
    },
    deleteGoods () {
      this.$emit('delete');
    },
    ...mapActions({
      'createGoodsToCart': 'cart/create_goods_to_cart',
      'modifyGoodsFromCart': 'cart/modify_goods_num_from_cart'
    }),
    addToCart () {
      if (this.inGoods.num > 0) {
        let goods = JSON.parse(JSON.stringify(this.inGoods));
        if (this.hasGoods(goods)) {
          this.modifyGoodsFromCart({goods: goods, num: goods.num});
        } else this.createGoodsToCart(goods);
      }
    }
  }
};
</script>

<style scoped>
  .box-card {
    height: 160px;
    width: 275px;
    margin-bottom: 10px;
    padding: 0;
  }

  .avatar {
    padding: 10px 8px 0 5px;
    float: left;
  }

  .avatar img {
    height: 100px;
    width: 100px;
  }

  .info {
    height: 115px;
    width: 160px;
    padding-top: 5px;
    text-align: left;
    float: right;
  }

  .goods-op {
    padding-right: 20px;
    float: right;
  }
</style>
