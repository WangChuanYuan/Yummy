<template>
  <el-card class="box-card clear-fix" :body-style="{ padding: '0px' }">
    <div class="clear-fix">
      <div class="avatar">
        <img :src="inGoods.saleInfo.avatar">
      </div>
      <div class="info">
        <span style="font-size: 20px; font-weight: bold">{{inGoods.saleInfo.name}}</span>
        <br/>
        <br/>
        <div style="font-size: 13px">
          <span>价格:</span>
          <span>{{inGoods.saleInfo.price}}</span>
        </div>
        <br/>
        <div style="font-size: 13px; white-space: nowrap; overflow: hidden; text-overflow: ellipsis">
          <span>今日剩余:</span>
          <span>{{inGoods.saleInfo.dLeft}}</span>
          <br/>
          <span>库存总量:</span>
          <span>{{inGoods.saleInfo.stock}}</span>
        </div>
      </div>
    </div>
    <div class="goods-op">
      <el-button type="text" @click="modify">修改</el-button>
      <el-button type="text">下架</el-button>
    </div>
  </el-card>
</template>

<script>
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
          saleInfo: {
            avatar: require('../../../assets/image/coffee.jpg'),
            name: 'rww',
            description: '',
            price: 0,
            dLeft: 0,
            stock: 100000,
            startDate: '',
            endDate: ''
          }
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
  methods: {
    modify () {
      this.$router.push({
        name: 'editGoods',
        params: {
          gid: this.inGoods.gid,
          aim: 'modify'
        }
      });
    }
  }
};
</script>

<style scoped>
  .box-card {
    height: 155px;
    width: 275px;
    margin-bottom: 10px;
    padding: 0;
  }

  .avatar {
    padding: 10px 8px 0 7px;
    float: left;
  }

  .avatar img {
    height: 100px;
    width: 100px;
  }

  .info {
    height: 120px;
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
