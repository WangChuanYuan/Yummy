<template>
  <div>
    <span>请在{{timeLimit}}分钟内完成支付，否则订单将自动取消</span>
    <div v-for="rid in Object.keys(bills)" :key="rid" class="bill center">
      <span>店家号: {{rid}}</span>
      <hr style="border:1px dashed var(--theme-blue)" size=1>
      <span>单品费: {{bills[rid].goodsTotal}}</span>
      <span>套餐费: {{bills[rid].combosTotal}}</span>
      <span>配送费: {{bills[rid].deliveryExp}}</span>
      <br/>
      <span>满减优惠: {{bills[rid].favour}}</span>
      <span>总费用: {{bills[rid].total}}</span>
      <span>会员优惠价: {{bills[rid].finalFee}}</span>
      <div>
        <el-button type="primary" size="mini" @click="$router.push('/memberCenter/orders')">去付款</el-button>
        <el-button type="success" size="mini" @click="$router.push(`/bookCenter/${rid}`)">再逛逛</el-button>
      </div>
    </div>
  </div>
</template>

<script>
import {ORDER_PAY_MINUTES_LIMIT} from '../../assets/js/attrib';

export default {
  name: 'OrderResult',
  props: {
    'bills': {
      type: Object,
      default: function () {
        return {};
      }
    }
  },
  data () {
    return {
      timeLimit: ORDER_PAY_MINUTES_LIMIT
    };
  }
};
</script>

<style scoped>
  .bill {
    height: 150px;
    width: 400px;
    font: 20px/1.5 Tahoma,Helvetica,Arial,'宋体',sans-serif;
    margin-top: 5px;
    margin-bottom: 20px;
    background-color: white;
  }
</style>
