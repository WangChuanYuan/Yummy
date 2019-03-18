<template>
  <el-popover
    placement="right"
    title="订单规则"
    trigger="hover">
    <span>订单下达2分钟内可选择无条件取消订单</span>
    <br/>
    <span>订单下达2分钟后未支付将自动取消订单，不扣除任何费用</span>
    <br/>
    <span>订单在系统预计送达时间40分钟后，将会默认收货</span>
    <br/>
    <span>订单支付后可且订单未完成前可选择退订，具体规则如下:</span>
    <br/>
    <span>1. 下单10分钟内退还订单金额88%</span>
    <br/>
    <span>2. 下单10-20分钟内退还订单金额83%</span>
    <br/>
    <span>3. 下单20-30分钟内退还订单金额78%</span>
    <br/>
    <span>4. 下单30-60分钟内退还订单金额50%</span>
    <br/>
    <span>5. 下单60分钟后退还订单金额30%</span>
    <br/>
    <span>6. 若超出预计送达时间30分钟无条件退订</span>
    <div v-if="role === 'restaurant'">
      <br/>
      <span>以下为Yummy!平台抽成规则</span>
      <br/>
      <span>会员订餐支付后将统一转由平台，在订单结束(包括正常收货与退订)时自动结算给商家</span>
      <br/>
      <span>平台将抽取每笔订单7%的费用(包括会员退订后商家本笔订单的实际所得)</span>
    </div>
    <i class="el-icon-info" slot="reference"></i>
  </el-popover>
</template>

<script>
export default {
  name: 'OrderRule',
  props: {
    'role': {
      type: String,
      default: 'member'
    }
  }
};
</script>

<style scoped>

</style>
