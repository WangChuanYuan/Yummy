<template>
  <div>
    <h3>我的订单</h3>
    <OrderRule/>
    <hr/>
    <OrderTable :orders="orders"></OrderTable>
  </div>
</template>

<script>
import OrderTable from '../order/OrderTable';
import Api from '../../assets/js/api';
import OrderRule from '../order/OrderRule';

export default {
  name: 'MemberOrders',
  components: {OrderRule, OrderTable},
  mounted () {
    Api.get('/get_member_orders', {mid: sessionStorage.getItem('id')}).then((data) => {
      if (data) this.orders = data;
    }).catch(() => {});
  },
  data () {
    return {
      orders: [
        {
          oid: 1,
          rid: 1,
          rAvatar: require('../../assets/image/cream.jpg'),
          mid: 1,
          mName: 'wcy',
          cardNo: '3323',
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
          status: 'ORDERED'
        }
      ]
    };
  }
};
</script>

<style scoped>

</style>
