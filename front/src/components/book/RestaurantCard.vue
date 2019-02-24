<template>
  <el-card class="box-card clear-fix" :body-style="{ padding: '0px' }" shadow="never">
    <div class="clear-fix">
      <div class="avatar">
        <img :src="inRestaurant.avatar">
      </div>
      <div class="info">
        <span style="font-size: 20px; font-weight: bold">{{inRestaurant.registerInfo.name}}</span>
        <br/>
        <div class="omission" style="font-size: 13px">
          <span>地址:</span>
          <span>{{inRestaurant.registerInfo.detailLocation}}</span>
        </div>
        <div style="font-size: 13px">
          <span>营业时间:</span>
          <br/>
          <span>{{inRestaurant.marketInfo.starHour}}-{{inRestaurant.marketInfo.endHour}}</span>
        </div>
        <br/>
        <div class="omission" style="font-size: 13px">
          <span>起送费:</span>
          <span>{{inRestaurant.marketInfo.leastExp}}</span>
          <span>配送费:</span>
          <span>{{inRestaurant.marketInfo.deliveryExp}}</span>
        </div>
      </div>
    </div>
    <div class="restaurant-op">
      <el-button type="text" @click="enter">进入</el-button>
    </div>
  </el-card>
</template>

<script>
import {RestaurantType} from '../../assets/js/attrib';

export default {
  name: 'RestaurantCard',
  props: {
    'restaurant': {
      type: Object,
      default: function () {
        return {
          id: '0000000',
          avatar: require('../../assets/image/city.jpg'),
          registerInfo: {
            name: '',
            type: RestaurantType.DELICACY,
            location: '',
            detailLocation: '',
            lng: 0,
            lat: 0
          },
          marketInfo: {
            balance: 0,
            phone: '',
            leastExp: 0,
            deliveryExp: 0,
            starHour: '08:00:00',
            endHour: '22:00:00'
          }
        };
      }
    }
  },
  data () {
    return {
      inRestaurant: JSON.parse(JSON.stringify(this.restaurant))
    };
  },
  watch: {
    restaurant (val) {
      this.inRestaurant = JSON.parse(JSON.stringify(val));
    }
  },
  methods: {
    enter () {
      this.$router.push(`/bookCenter/${this.inRestaurant.id}`);
    }
  }
};
</script>

<style scoped>
  .box-card {
    height: 160px;
    width: 275px;
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

  .restaurant-op {
    padding-right: 20px;
    float: right;
  }
</style>
