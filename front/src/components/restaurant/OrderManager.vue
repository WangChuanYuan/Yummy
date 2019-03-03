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
import Api from '../../assets/js/api';

export default {
  name: 'OrderManager',
  components: {OrderRule, OrderTable},
  mounted () {
    Api.get('/get_restaurant_orders', {rid: sessionStorage.getItem('id')}).then((data) => {
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
