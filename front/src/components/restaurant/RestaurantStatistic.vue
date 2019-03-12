<template>
  <div>
    <h3>门店数据</h3>
    <span>收入{{valueTotal(incomeOfOrderStatus)}}元</span>
    &nbsp;&nbsp;
    <span>订单{{valueTotal(orderNumOfOrderStatus)}}单</span>
    <hr/>
    <el-date-picker
      v-model="dates"
      type="daterange"
      value-format="yyyy-MM-dd"
      range-separator="至"
      start-placeholder="起始日期"
      end-placeholder="结束日期"
      placeholder="选择日期">
    </el-date-picker>
    <el-select v-model="memberLevel" clearable placeholder="选择会员等级" :value="0">
      <el-option v-for="i in [0, 1, 2, 3, 4, 5]" :key="i" :value="i" :label="i"/>
    </el-select>
    <el-input type="number" v-model="finalFeeLowerLimit" style="width: 150px" placeholder="订单金额"></el-input>
    <span>-</span>
    <el-input type="number" v-model="finalFeeUpperLimit" style="width: 150px" placeholder="订单金额"></el-input>
    <el-button type="primary" size="mini" @click="getData">查询</el-button>
    <el-row>
      <el-container>
        <el-aside width="400px">
          <el-table :data="orders" height="500">
            <el-table-column prop="orderTime" label="下单时间" width="100"/>
            <el-table-column label="订单金额" width="60">
              <template slot-scope="scope">
                <span>{{scope.row.bill.finalFee}}</span>
              </template>
            </el-table-column>
            <el-table-column label="营收金额" width="50">
              <template slot-scope="scope">
                <span>{{scope.row.bill.actualFee * (1 - tax)}}</span>
              </template>
            </el-table-column>
            <el-table-column label="订单结果" width="60">
              <template slot-scope="scope">
                <span>{{status[scope.row.status].label}}</span>
              </template>
            </el-table-column>
            <el-table-column prop="mid" label="会员账号"/>
          </el-table>
        </el-aside>
        <el-main>
          <LinearChart :chart-data="incomeOfDate" :height="500" :width="700" title-text="日收入"></LinearChart>
        </el-main>
      </el-container>
    </el-row>
    <el-row>
      <el-col :span="10" :offset="2">
        <LinearChart type="pie" :chart-data="incomeOfOrderStatus" title-text="营收额-收入类型"/>
      </el-col>
      <el-col :span="12">
        <LinearChart type="bar" :chart-data="incomeOfMemberLevel" :width="500" title-text="营收额-会员等级" y-text="营收额"/>
      </el-col>
    </el-row>
    <el-row>
      <el-col :span="10" :offset="2">
        <LinearChart type="pie" :chart-data="orderNumOfOrderStatus" title-text="订单数-订单结果"/>
      </el-col>
      <el-col :span="12">
        <LinearChart type="bar" :chart-data="orderNumOfMemberLevel" :width="500" title-text="订单数-会员等级" y-text="订单数"/>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import Api from '../../assets/js/api';
import {ORDER_TAX, OrderStatus, RestaurantType} from '../../assets/js/attrib';
import LinearChart from '../charts/LinearChart';

export default {
  name: 'RestaurantStatistic',
  components: {LinearChart},
  data () {
    return {
      tax: ORDER_TAX,
      status: OrderStatus,
      types: RestaurantType,
      /* conditions */
      dates: [],
      memberLevel: '',
      finalFeeLowerLimit: '',
      finalFeeUpperLimit: '',
      /* data */
      orders: [],
      incomeOfDate: [],
      incomeOfOrderStatus: [],
      incomeOfMemberLevel: [],
      orderNumOfOrderStatus: [],
      orderNumOfMemberLevel: []
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
      let param = {rid: sessionStorage.getItem('id')};
      if (this.dates && this.dates[0]) {
        param.dateFrom = this.dates[0];
      }
      if (this.dates && this.dates[1]) {
        param.dateTo = this.dates[1];
      }
      if (this.member) {
        param.memberLevel = this.memberLevel;
      }
      if (this.finalFeeLowerLimit) param.finalFeeLowerLimit = this.finalFeeLowerLimit;
      if (this.finalFeeUpperLimit) param.finalFeeUpperLimit = this.finalFeeUpperLimit;
      return param;
    },
    getData () {
      this.getOrders();
      this.getIncomeOfDate();
      this.getIncomeOfOrderStatus();
      this.getIncomeOfMemberLevel();
      this.getOrderNumOfOrderStatus();
      this.getOrderNumOfMemberLevel();
    },
    getOrders () {
      Api.get('/get_orders', {
        condition: JSON.stringify(this.getParam())
      }).then((data) => {
        if (data) this.orders = data;
      }).catch(() => {
      });
    },
    getIncomeOfDate () {
      Api.get('/income_of_date', {
        condition: JSON.stringify(this.getParam())
      }).then((data) => {
        if (data) {
          this.incomeOfDate = data;
        }
      }).catch(() => {
      });
    },
    getIncomeOfOrderStatus () {
      Api.get('/income_of_order_status', {
        condition: JSON.stringify(this.getParam())
      }).then((data) => {
        if (data) {
          this.incomeOfOrderStatus = data.map(
            item => {
              item.key = (item.key === this.status.FINISHED.value ? '点餐' : '退订');
              return item;
            });
        }
      }).catch(() => {
      });
    },
    getIncomeOfMemberLevel () {
      Api.get('/income_of_member_level', {
        condition: JSON.stringify(this.getParam())
      }).then((data) => {
        if (data) {
          this.incomeOfMemberLevel = data;
        }
      }).catch(() => {
      });
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
      }).catch(() => {
      });
    },
    getOrderNumOfMemberLevel () {
      Api.get('/order_num_of_member_level', {
        condition: JSON.stringify(this.getParam())
      }).then((data) => {
        if (data) {
          this.orderNumOfMemberLevel = data;
        }
      }).catch(() => {
      });
    }
  }
};
</script>

<style scoped>

</style>
