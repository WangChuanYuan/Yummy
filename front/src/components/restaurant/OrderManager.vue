<template>
  <div>
    <h3>订单管理</h3>
    <OrderRule role="restaurant"/>
    <hr/>
    <OrderTable :orders="orders" role="restaurant"/>
  </div>
</template>

<script>
import OrderTable from '../order/OrderTable';
import OrderRule from '../order/OrderRule';

export default {
  name: 'OrderManager',
  components: {OrderRule, OrderTable},
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
          status: 'FINISHED'
        }
      ]
    };
  }
};
</script>

<style scoped>

</style>
