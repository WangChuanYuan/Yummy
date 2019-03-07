<template>
  <div>
    <el-table :data="inOrders" stripe :max-height="maxHeight" style="margin-top: 20px; margin-bottom: 50px">
      <el-table-column prop="orderTime" label="下单时间" width="80"/>
      <el-table-column label="订单内容">
        <template slot-scope="scope">
          <div class="clear-fix">
            <div style="float: left">
              <img
                :src="scope.row.ravatar"
                :class="{link : role === 'member'}"
                style="height: 70px; width: 70px; border-radius: 70px"
                @click="visit(scope.$index)"/>
            </div>
            <div class="omission" style="padding-left: 10px; color: var(--theme-medium-grey)">
              <span>订单号:{{scope.row.oid}}</span>
              <br/>
              <span>单品:{{orderShot(scope.row.goods)}}</span>
              <br/>
              <span>套餐:{{orderShot(scope.row.combos)}}</span>
              <br/>
              <span>备注:{{scope.row.tip ? scope.row.tip : '无'}}</span>
            </div>
          </div>
        </template>
      </el-table-column>
      <el-table-column label="地址" width="50">
        <template slot-scope="scope">
          <el-popover
            placement="right"
            title="地址"
            trigger="hover">
            <div>
              <span>会员号: {{scope.row.mid}}</span>
              <br/>
              <span>接收人: {{scope.row.mname}}</span>
              <br/>
              <span>详细地址: {{scope.row.detailLocation}}</span>
            </div>
            <el-button type="text" slot="reference">地址</el-button>
          </el-popover>
        </template>
      </el-table-column>
      <el-table-column label="账单" width="50">
        <template slot-scope="scope">
          <el-popover
            placement="right"
            title="账单"
            trigger="hover">
            <div>
              <span>单品: {{orderShot(scope.row.goods)}}</span>
              <br/>
              <span>套餐: {{orderShot(scope.row.combos)}}</span>
              <br/>
              <span>单品费: {{scope.row.bill.goodsTotal}}</span>
              <span>套餐费: {{scope.row.bill.combosTotal}}</span>
              <span>配送费: {{scope.row.bill.deliveryExp}}</span>
              <br/>
              <span>满减优惠: {{scope.row.bill.favour}}</span>
              <span>总费用: {{scope.row.bill.total}}</span>
              <span>会员优惠价: {{scope.row.bill.finalFee}}</span>
              <br/>
              <span>实际消费: {{scope.row.bill.actualFee}}</span>
              <br/>
              <span>支付卡号: {{scope.row.cardNo}}</span>
            </div>
            <el-button type="text" slot="reference">账单</el-button>
          </el-popover>
        </template>
      </el-table-column>
      <el-table-column label="送达时间" width="80">
        <template slot-scope="scope">
          <div v-if="scope.row.status === 'FINISHED'">{{scope.row.arrivalTime}}</div>
          <div v-else>预期: {{scope.row.predictedArrivalTime}}</div>
        </template>
      </el-table-column>
      <el-table-column label="状态" width="50">
        <template slot-scope="scope">
          <div>{{status[scope.row.status].label}}</div>
        </template>
      </el-table-column>
      <el-table-column label="操作" width="150">
        <template slot-scope="scope">
          <div v-if="role === 'member'">
            <div v-if="scope.row.status === 'ORDERED'">
              <el-button type="primary" size="mini" @click="isPaying = true; payForm.index = scope.$index; payForm.cardNo = scope.row.cardNo">支付</el-button>
              <el-button type="info" size="mini" @click="cancel(scope.$index)">取消</el-button>
            </div>
            <div v-else-if="scope.row.status === 'PAYED' || scope.row.status === 'DISPATCHED'">
              <el-button type="primary" size="mini" @click="confirm(scope.$index)" :disabled="scope.row.status !== 'DISPATCHED'">收货</el-button>
              <el-button type="danger" size="mini" @click="unsubscribe(scope.$index)">退订</el-button>
            </div>
            <div v-else>
              <el-button type="success" size="mini" @click="visit(scope.$index)">再去看看</el-button>
            </div>
          </div>
          <div v-else-if="role === 'restaurant'">
            <div v-if="scope.row.status === 'ORDERED' || scope.row.status === 'PAYED'">
              <el-button type="primary" size="mini" :disabled="scope.row.status !== 'PAYED'"
                         @click="dispatch(scope.$index)">派送
              </el-button>
            </div>
            <div v-else-if="scope.row.status === 'DISPATCHED'">
              <i>已在派送中</i>
            </div>
            <div v-else><i>无</i></div>
          </div>
        </template>
      </el-table-column>
    </el-table>
    <!-- 支付框 -->
    <el-dialog title="支付" :visible.sync="isPaying" width="30%">
      <el-form :model="payForm" ref="payForm" :rules="payRule">
        <el-form-item prop="cardNo" label="银行卡号">
          <el-input v-model="payForm.cardNo" placeholder="银行卡号"></el-input>
        </el-form-item>
        <el-form-item prop="password" label="密码">
          <el-input type="password" v-model="payForm.password" placeholder="密码"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="pay('payForm')" style="margin-left: 35%">支付</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>
  </div>
</template>

<script>
import {Code, OrderStatus} from '../../assets/js/attrib';
import Api from '../../assets/js/api';

export default {
  name: 'OrderTable',
  props: {
    'orders': {
      type: Array,
      default: function () {
        return [];
      }
    },
    'maxHeight': {
      type: Number,
      default: 800
    },
    'role': {
      type: String,
      default: 'member'
    }
  },
  watch: {
    orders (val) {
      this.inOrders = JSON.parse(JSON.stringify(val));
    }
  },
  data () {
    return {
      inOrders: JSON.parse(JSON.stringify(this.orders)),
      status: OrderStatus,
      /* form */
      isPaying: false,
      payForm: {
        index: null,
        cardNo: '',
        password: ''
      },
      payRule: {
        cardNo: [
          {
            required: true,
            message: '请输入银行卡号',
            trigger: 'blur'
          }
        ],
        password: [
          {
            required: true,
            message: '请输入密码',
            trigger: 'blur'
          }
        ]
      }
    };
  },
  methods: {
    orderShot (items) {
      if (items.length === 0) return '无';
      let shot = '';
      for (let i in items) {
        let item = items[i];
        shot += item.name + ' ' + 'x' + item.num + '/';
      }
      return shot;
    },
    visit (index) {
      if (this.role === 'member') this.$router.push(`/bookCenter/${this.inOrders[index].rid}`);
    },
    pay (formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          Api.post('/pay_order', {
            oid: this.inOrders[this.payForm.index].oid,
            bankcardPassword: this.payForm.password
          }).then((data) => {
            if (data.code === Code.SUCCESS) {
              this.$message.success(data.msg);
              this.inOrders[this.payForm.index].status = OrderStatus.PAYED.value;
            } else this.$message.warning(data.msg);
            this.isPaying = false;
          }).catch(() => {
            this.$message.warning('错误');
            this.isPaying = false;
          });
        }
      });
    },
    cancel (index) {
      this.$confirm('确定取消订单？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        let _this = this;
        Api.post('/cancel_order', {
          oid: _this.inOrders[index].oid
        }).then((data) => {
          if (data.code === Code.SUCCESS) {
            _this.$message.success(data.msg);
            _this.inOrders[index].status = OrderStatus.CANCELED.value;
          } else {
            _this.$message.warning(data.msg);
          }
        });
      }).catch(() => {});
    },
    confirm (index) {
      this.$confirm('订单已完成？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'success'
      }).then(() => {
        let _this = this;
        Api.post('/confirm_order', {
          oid: _this.inOrders[index].oid
        }).then((data) => {
          if (data.code === Code.SUCCESS) {
            _this.$message.success(data.msg);
            _this.inOrders[index].status = OrderStatus.FINISHED.value;
          } else {
            _this.$message.warning(data.msg);
          }
        });
      }).catch(() => {});
    },
    unsubscribe (index) {
      this.$confirm('确定退订订单？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        let _this = this;
        Api.post('/unsubscribe_order', {
          oid: _this.inOrders[index].oid
        }).then((data) => {
          if (data.code === Code.SUCCESS) {
            _this.$message.success(data.msg);
            _this.inOrders[index].status = OrderStatus.UNSUBSCRIBED.value;
          } else {
            _this.$message.warning(data.msg);
          }
        });
      }).catch(() => {});
    },
    dispatch (index) {
      Api.post('/dispatch_order', {
        oid: this.inOrders[index].oid
      }).then((data) => {
        if (data.code === Code.SUCCESS) {
          this.$message.success(data.msg);
          this.inOrders[index].status = OrderStatus.DISPATCHED.value;
        }
        this.isPaying = false;
      }).catch(() => { this.$message.warning('错误'); });
    }
  }
};
</script>

<style scoped>
  .link {
    cursor: pointer;
  }
</style>
