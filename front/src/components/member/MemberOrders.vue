<template>
  <div>
    <h3>我的订单</h3>
    <el-popover
      placement="right"
      title="订单规则"
      trigger="hover">
      <span>订单下达2分钟内可选择无条件取消订单</span>
      <br/>
      <span>订单下达2分钟后未支付将自动取消订单，不扣除任何费用</span>
      <br/>
      <span>订单支付后可且订单未完成前可选择退订，具体规则如下:</span>
      <br/>
      <span>1. 下单10分钟内退还订单金额90%</span>
      <br/>
      <span>2. 下单10-20分钟内退还订单金额85%</span>
      <br/>
      <span>3. 下单20-30分钟内退还订单金额80%</span>
      <br/>
      <span>4. 下单30-60分钟内退还订单金额40%</span>
      <br/>
      <span>5. 下单60分钟后退还订单金额20%</span>
      <br/>
      <span>6. 若超出预计送达时间30分钟无条件退订</span>
      <i class="el-icon-info" slot="reference"></i>
    </el-popover>
    <hr/>
    <OrderTable :orders="orders"></OrderTable>
  </div>
</template>

<script>
import OrderTable from '../order/OrderTable';

export default {
  name: 'MemberOrders',
  components: {OrderTable},
  data () {
    return {
      orders: [
        {
          oid: 1,
          rid: 1,
          rAvatar: require('../../assets/image/cream.jpg'),
          mid: 1,
          mName: 'wcy',
          goods: [
            {name: 'abc', num: 1}
          ],
          combos: [],
          bill: {
            goodsTotal: 100,
            combosTotal: 100,
            deliveryExp: 0,
            total: 200,
            finalFee: 180
          },
          rate: 10,
          predictedArrivalTime: '2018-09-01 11:02:02',
          arrivalTime: '2018-09-01 11:02:02',
          status: 'PAYED'
        }
      ]
    };
  }
};
</script>

<style scoped>

</style>
