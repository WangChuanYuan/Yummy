<template>
  <div>
    <h3>Yummy! 数据统计</h3>
    <hr/>
    <el-row>
      <el-col :span="4" :offset="2">
        <div>
          <img :src="require('../../assets/image/icons/user_icon.png')"/>
          <br/>
          <span style="font-size: 20px; font-weight: bold">
            会员&nbsp;
            <span style="color: firebrick">{{valueTotal(memberNumOfLevel)}} </span>
            &nbsp;人
          </span>
        </div>
      </el-col>
      <el-col :span="4" :offset="2">
        <div>
          <img :src="require('../../assets/image/icons/house_icon.png')"/>
          <br/>
          <span style="font-size: 20px; font-weight: bold">
            门店&nbsp;
            <span style="color: firebrick">{{valueTotal(restaurantNumOfType)}} </span>
            &nbsp;家
          </span>
        </div>
      </el-col>
      <el-col :span="4" :offset="2">
        <div>
          <img :src="require('../../assets/image/icons/bill_icon.png')"/>
          <br/>
          <span style="font-size: 20px; font-weight: bold">
            订单&nbsp;
            <span style="color: firebrick">{{valueTotal(orderNumOfOrderStatus)}} </span>
            &nbsp;笔
          </span>
        </div>
      </el-col>
      <el-col :span="4" :offset="2">
        <div>
          <img :src="require('../../assets/image/icons/cash_icon.png')"/>
          <br/>
          <span style="font-size: 20px; font-weight: bold">
            盈利&nbsp;
            <span style="color: firebrick">{{valueTotal(incomeOfRestaurantType).toFixed(2)}} </span>
            &nbsp;元
          </span>
        </div>
      </el-col>
    </el-row>
    <el-row style="padding-top: 50px">
      <el-col :span="4" :offset="2">
        <LinearChart type="bar" :chart-data="memberNumOfLevel" title-text="会员等级分布"></LinearChart>
      </el-col>
      <el-col :span="8" :offset="4">
        <LinearChart type="line" :chart-data="incomeOfDate" title-text="日盈利" :width="600"></LinearChart>
      </el-col>
    </el-row>
    <el-row style="padding-top: 50px">
      <el-col :span="12" :offset="2">
        <LinearChart type="line" :chart-data="orderNumOfDate" title-text="日订单数" :width="600"></LinearChart>
      </el-col>
      <el-col :span="4" :offset="2">
        <LinearChart type="pie" :chart-data="orderNumOfOrderStatus" title-text="订单状态分布"></LinearChart>
      </el-col>
    </el-row>
    <el-row style="padding-top: 50px">
      <el-col :span="6" :offset="2">
        <LinearChart type="pie" :chart-data="restaurantNumOfType" title-text="餐厅类型分布"></LinearChart>
      </el-col>
      <el-col :span="6" :offset="1">
        <LinearChart type="pie" :chart-data="incomeOfRestaurantType" title-text="盈利-餐厅类型"></LinearChart>
      </el-col>
      <el-col :span="6" :offset="1">
        <LinearChart type="bar" :chart-data="incomeOfMemberLevel" title-text="盈利-会员等级"></LinearChart>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import LinearChart from '../charts/LinearChart';
import Api from '../../assets/js/api';
import {OrderStatus, RestaurantType} from '../../assets/js/attrib';

export default {
  name: 'StatisticViewer',
  components: {LinearChart},
  data () {
    return {
      types: RestaurantType,
      status: OrderStatus,
      /* data */
      incomeOfDate: [],
      incomeOfRestaurantType: [],
      incomeOfMemberLevel: [],
      orderNumOfDate: [],
      memberNumOfLevel: [],
      restaurantNumOfType: [],
      orderNumOfOrderStatus: []
    };
  },
  mounted () {
    this.getData();
  },
  methods: {
    valueTotal (data) {
      let total = 0;
      data.map(d => {
        total += d.value;
      });
      return total;
    },
    getData () {
      this.getIncomeOfDate();
      this.getIncomeOfRestaurantType();
      this.getIncomeOfMemberLevel();
      this.getOrderNumOfDate();
      this.getOrderNumOfOrderStatus();
      this.getMemberNumOfLevel();
      this.getRestaurantNumOfType();
    },
    getIncomeOfDate () {
      Api.get('/income_of_date').then((data) => {
        if (data) this.incomeOfDate = data;
      }).catch(() => {});
    },
    getIncomeOfRestaurantType () {
      Api.get('/income_of_restaurant_type').then((data) => {
        if (data) {
          this.incomeOfRestaurantType = data.map(
            item => {
              item.key = this.types[item.key].label;
              return item;
            }
          );
        }
      }).catch(() => {});
    },
    getIncomeOfMemberLevel () {
      Api.get('/income_of_member_level').then((data) => {
        if (data) {
          this.incomeOfMemberLevel = data;
        }
      }).catch(() => {});
    },
    getOrderNumOfDate () {
      Api.get('/order_num_of_date').then((data) => {
        if (data) {
          this.orderNumOfDate = data;
        }
      }).catch(() => {});
    },
    getOrderNumOfOrderStatus () {
      Api.get('/order_num_of_order_status').then((data) => {
        if (data) {
          this.orderNumOfOrderStatus = data.map(
            item => {
              item.key = this.status[item.key].label;
              return item;
            });
        }
      }).catch(() => {});
    },
    getMemberNumOfLevel () {
      Api.get('/member_num_of_level').then((data) => {
        if (data) {
          this.memberNumOfLevel = data;
        }
      }).catch(() => {});
    },
    getRestaurantNumOfType () {
      Api.get('/restaurant_num_of_type').then((data) => {
        if (data) {
          this.restaurantNumOfType = data.map(
            item => {
              item.key = this.types[item.key].label;
              return item;
            });
        }
      }).catch(() => {});
    }
  }
};
</script>

<style scoped>

</style>
