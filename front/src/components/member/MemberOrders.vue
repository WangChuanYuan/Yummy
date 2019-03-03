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
      orders: []
    };
  }
};
</script>

<style scoped>

</style>
