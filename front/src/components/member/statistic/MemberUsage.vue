<template>
  <div>
    <h3>订单数统计</h3>
    <hr/>
    <el-date-picker
      v-model="dates"
      type="daterange"
      value-format="yyyy-MM-dd"
      range-separator="至"
      start-placeholder="起始日期"
      end-placeholder="结束日期"
      placeholder="选择日期"
      style="width: 250px">
    </el-date-picker>
    <el-select v-model="restaurantType" clearable placeholder="选择餐厅类型" :value="types.DELICACY.value">
      <el-option v-for="type in types" :key="type.value" :value="type.value" :label="type.label"/>
    </el-select>
    <el-input type="number" v-model="finalFeeLowerLimit" style="width: 150px" placeholder="订单金额"></el-input>
    <span>-</span>
    <el-input type="number" v-model="finalFeeUpperLimit" style="width: 150px" placeholder="订单金额"></el-input>
    <el-button type="primary" size="mini" @click="getData">查询</el-button>
    <el-container>
      <el-aside>
        <el-table :data="orders" height="400">
          <el-table-column prop="orderTime" label="下单时间" width="100"/>
          <el-table-column label="订单状态" width="60">
            <template slot-scope="scope">
              <span>{{status[scope.row.status].label}}</span>
            </template>
          </el-table-column>
          <el-table-column label="订单金额" width="50">
            <template slot-scope="scope">
              <span>{{scope.row.bill.finalFee}}</span>
            </template>
          </el-table-column>
          <el-table-column prop="rname" label="门店"/>
        </el-table>
      </el-aside>
      <el-main>
        <el-row>
          <el-col :span="12">
            <LinearChart type="pie" :chart-data="usageOfOrderStatus" title-text="订单数-订单结果"/>
          </el-col>
          <el-col :span="12">
            <LinearChart type="pie" :chart-data="usageOfRestaurantType" title-text="订单数-门店类型"/>
          </el-col>
        </el-row>
      </el-main>
    </el-container>
  </div>
</template>

<script>
import Api from '../../../assets/js/api';
import {OrderStatus, RestaurantType} from '../../../assets/js/attrib';
import LinearChart from '../../charts/LinearChart';

export default {
  name: 'MemberConsume',
  components: {LinearChart},
  data () {
    return {
      status: OrderStatus,
      types: RestaurantType,
      /* conditions */
      dates: [],
      restaurantType: '',
      finalFeeLowerLimit: '',
      finalFeeUpperLimit: '',
      /* data */
      orders: [],
      usageOfOrderStatus: [],
      usageOfRestaurantType: []
    };
  },
  mounted () {
    this.getData();
  },
  methods: {
    getParam () {
      let param = {mid: sessionStorage.getItem('id')};
      if (this.dates && this.dates[0]) {
        param.dateFrom = this.dates[0];
      }
      if (this.dates && this.dates[1]) {
        param.dateTo = this.dates[1];
      }
      if (this.restaurantType) {
        param.restaurantType = this.restaurantType;
      }
      if (this.finalFeeLowerLimit) param.finalFeeLowerLimit = this.finalFeeLowerLimit;
      if (this.finalFeeUpperLimit) param.finalFeeUpperLimit = this.finalFeeUpperLimit;
      return param;
    },
    getData () {
      this.getUsageOfOrders();
      this.getUsageOfOrderStatus();
      this.getUsageOfRestaurantType();
    },
    getUsageOfOrders () {
      Api.get('/get_orders', this.getParam()).then((data) => {
        if (data) this.orders = data;
      }).catch(() => {});
    },
    getUsageOfOrderStatus () {
      Api.get('/usage_of_order_status', this.getParam()).then((data) => {
        if (data) {
          this.usageOfOrderStatus = data.map(
            item => {
              item.key = this.status[item.key].label;
              return item;
            });
        }
      }).catch(() => {});
    },
    getUsageOfRestaurantType () {
      Api.get('/usage_of_restaurant_type', this.getParam()).then((data) => {
        if (data) {
          this.usageOfRestaurantType = data.map(
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
