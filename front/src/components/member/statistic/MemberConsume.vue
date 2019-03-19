<template>
  <div>
    <h3>消费额统计</h3>
    <span>合计: {{valueTotal(consumeOfOrderStatus)}}元</span>
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
    <el-input type="number" v-model="actualFeeLowerLimit" style="width: 150px" placeholder="消费金额"></el-input>
    <span>-</span>
    <el-input type="number" v-model="actualFeeUpperLimit" style="width: 150px" placeholder="消费金额"></el-input>
    <el-button type="primary" size="mini" @click="getData">查询</el-button>
    <el-container>
      <el-aside>
        <el-table :data="consumedOrders" height="400">
          <el-table-column prop="orderTime" label="下单时间" width="100"/>
          <el-table-column label="消费类型" width="60">
            <template slot-scope="scope">
              <span>{{scope.row.status === status.UNSUBSCRIBED.value ? '退订' : '点餐'}}</span>
            </template>
          </el-table-column>
          <el-table-column label="消费金额" width="50">
            <template slot-scope="scope">
              <span>{{scope.row.bill.actualFee}}</span>
            </template>
          </el-table-column>
          <el-table-column prop="rname" label="门店"/>
        </el-table>
      </el-aside>
      <el-main>
        <el-row>
          <el-col :span="12">
            <LinearChart type="pie" :chart-data="consumeOfOrderStatus" title-text="消费额-消费类型" :height="400"/>
          </el-col>
          <el-col :span="12">
            <LinearChart type="pie" :chart-data="consumeOfRestaurantType" title-text="消费额-门店类型" :height="400"/>
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
      actualFeeLowerLimit: '',
      actualFeeUpperLimit: '',
      /* data */
      consumedOrders: [],
      consumeOfOrderStatus: [],
      consumeOfRestaurantType: []
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
      if (this.actualFeeLowerLimit) param.actualFeeLowerLimit = this.actualFeeLowerLimit;
      if (this.actualFeeUpperLimit) param.actualFeeUpperLimit = this.actualFeeUpperLimit;
      return param;
    },
    getData () {
      this.getConsumedOrders();
      this.getConsumeOfOrderStatus();
      this.getConsumeOfRestaurantType();
    },
    getConsumedOrders () {
      Api.get('/get_orders', {
        condition: JSON.stringify(this.getParam())
      }).then((data) => {
        if (data) {
          let consumedOrders = [];
          data.map(order => {
            if (order.bill.actualFee > 0) consumedOrders.push(order);
          });
          this.consumedOrders = consumedOrders;
        }
      }).catch(() => {});
    },
    getConsumeOfOrderStatus () {
      Api.get('/consume_of_order_status', {
        condition: JSON.stringify(this.getParam())
      }).then((data) => {
        let total = {'点餐': 0, '退订': 0};
        if (data) {
          data.map(item => {
            if (item.key === this.status.UNSUBSCRIBED.value) {
              total['退订'] += item.value;
            } else total['点餐'] += item.value;
          });
          let consumes = [];
          if (total['点餐'] !== 0) consumes.push({key: '点餐', value: Number(total['点餐'].toFixed(2))});
          if (total['退订'] !== 0) consumes.push({key: '退订', value: Number(total['退订'].toFixed(2))});
          this.consumeOfOrderStatus = consumes;
        }
      }).catch(() => {});
    },
    getConsumeOfRestaurantType () {
      Api.get('/consume_of_restaurant_type', {
        condition: JSON.stringify(this.getParam())
      }).then((data) => {
        if (data) {
          this.consumeOfRestaurantType = data.map(
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
