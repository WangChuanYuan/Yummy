<template>
  <div>
    <h3>注册信息</h3>
    <hr/>
    <RegisterForm aim="modify"/>
  </div>
</template>

<script>
import RegisterForm from './RegisterForm';

export default {
  name: 'RestaurantInfo',
  components: {RegisterForm}
};
</script>

<style scoped>

</style>
