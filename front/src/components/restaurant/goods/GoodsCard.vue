<template>
  <el-card class="box-card clear-fix" :body-style="{ padding: '0px' }">
    <div class="clear-fix">
      <div class="avatar">
        <img :src="inGoods.saleInfo.avatar">
      </div>
      <div class="info">
        <span>{{inGoods.saleInfo.name}}</span>
      </div>
    </div>
    <div class="goods-op">
      <el-button type="text">修改</el-button>
      <el-button type="text">下架</el-button>
    </div>
  </el-card>
</template>

<script>
export default {
  name: 'GoodsCard',
  props: {
    'goods': {
      type: Object,
      default: function () {
        return {
          gid: 0,
          saleInfo: {
            avatar: require('../../../assets/image/coffee.jpg')
          }
        };
      }
    }
  },
  data () {
    return {
      editing: false,
      inGoods: JSON.parse(JSON.stringify(this.goods))
    };
  },
  watch: {
    goods (val) {
      this.inGoods = JSON.parse(JSON.stringify(val));
    }
  }
};
</script>

<style scoped>
  .box-card {
    width: 350px;
    height: 200px;
    margin: 0;
    padding: 0;
    white-space: nowrap;
    overflow: hidden;
    text-overflow: ellipsis;
  }

  .avatar {
    padding: 10px 10px 0 10px;
    float: left;
  }

  .avatar img {
    height: 150px;
    width: 150px;
  }

  .info {
    height: 160px;
    width: 150px;
    float: right;
  }

  .goods-op {
    padding-right: 20px;
    float: right;
  }
</style>
