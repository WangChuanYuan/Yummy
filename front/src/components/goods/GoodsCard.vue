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
          <span>今日剩余:</span>
          <span>{{inGoods.dLeft}}</span>
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
      <el-input-number :min="0" :precision="0" size="mini"></el-input-number>
      <el-button type="text">购买</el-button>
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
          cgid: 0,
          category: '',
          avatar: require('../../assets/image/oil.jpg'),
          name: '',
          description: '',
          price: 0,
          dLeft: 0,
          stock: 0,
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
    padding: 10px 8px 0 7px;
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
