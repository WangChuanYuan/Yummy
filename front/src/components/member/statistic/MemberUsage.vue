<template>
  <div>
    <h3>订单数统计</h3>
    <span>合计: {{valueTotal(orderNumOfOrderStatus)}}单</span>
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
            <LinearChart type="pie" :chart-data="orderNumOfOrderStatus" title-text="订单数-订单结果"/>
          </el-col>
          <el-col :span="12">
            <LinearChart type="pie" :chart-data="orderNumOfRestaurantType" title-text="订单数-门店类型"/>
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
      orderNumOfOrderStatus: [],
      orderNumOfRestaurantType: []
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
      this.getOrders();
      this.getOrderNumOfOrderStatus();
      this.getOrderNumOfRestaurantType();
    },
    getOrders () {
      Api.get('/get_orders', {
        condition: JSON.stringify(this.getParam())
      }).then((data) => {
        if (data) this.orders = data;
      }).catch(() => {});
    },
    getOrderNumOfOrderStatus () {
      Api.get('/order_num_of_order_status', {
        condition: JSON.stringify(this.getParam())
      }).then((data) => {
        if (data) {
          this.orderNumOfOrderStatus = data.map(
            item => {
              item.key = this.status[item.key].label;
              return item;
            });
        }
      }).catch(() => {});
    },
    getOrderNumOfRestaurantType () {
      Api.get('/order_num_of_restaurant_type', {
        condition: JSON.stringify(this.getParam())
      }).then((data) => {
        if (data) {
          this.orderNumOfRestaurantType = data.map(
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
