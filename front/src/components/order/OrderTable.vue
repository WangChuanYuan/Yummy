<template>
  <el-table :data="orders" stripe>
    <el-table-column prop="orderTime" label="下单时间" width="80"/>
    <el-table-column label="订单内容">
      <template slot-scope="scope">
        <div class="clear-fix">
          <div style="float: left">
            <img
              :src="scope.row.rAvatar"
              :class="{link : role === 'member'}"
              style="height: 70px; width: 70px; border-radius: 70px"
              @click="visit(scope.row.rid)"/>
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
            <span>接收人: {{scope.row.mName}}</span>
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
            <span>总费用: {{scope.row.bill.total}}</span>
            <span>会员优惠价: {{scope.row.bill.finalFee}}</span>
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
            <el-button type="primary" size="mini">支付</el-button>
            <el-button type="info" size="mini">取消</el-button>
          </div>
          <div v-else-if="scope.row.status === 'PAYED' || scope.row.status === 'DISPATCHED'">
            <el-button type="primary" size="mini">收货确认</el-button>
            <el-button type="danger" size="mini">退订</el-button>
          </div>
          <div v-else>
            <el-button type="success" size="mini" @click="visit(scope.row.rid)">再去看看</el-button>
          </div>
          <!--<div v-else>-->
            <!--<el-rate v-model="scope.row.rate"></el-rate>-->
          <!--</div>-->
        </div>
        <div v-else-if="role === 'restaurant'">
          <div v-if="scope.row.status === 'ORDERED' || scope.row.status === 'PAYED'">
            <el-button type="primary" size="mini" :disabled="scope.row.status === 'ORDERED'">派送</el-button>
          </div>
          <div v-else-if="scope.row.status === 'DISPATCHED'">
            <i>已在派送中</i>
          </div>
          <div v-else><i>无</i></div>
        </div>
      </template>
    </el-table-column>
  </el-table>
</template>

<script>
import {OrderStatus} from '../../assets/js/attrib';

export default {
  name: 'OrderTable',
  props: {
    'orders': {
      type: Array,
      default: function () {
        return [];
      }
    },
    'role': {
      type: String,
      default: 'member'
    }
  },
  data () {
    return {
      status: OrderStatus
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
    visit (rid) {
      if (this.role === 'member') this.$router.push(`/bookCenter/${rid}`);
    }
  }
};
</script>

<style scoped>
  .link {
    cursor: pointer;
  }
</style>
