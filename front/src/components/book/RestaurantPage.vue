<template>
  <el-container>
    <el-header>
      <CategorySelector :categories="restaurantType" label="label" value="value"/>
    </el-header>
    <el-main>
    </el-main>
  </el-container>
</template>

<script>
import CategorySelector from '../CategorySelector';
import {RestaurantType} from '../../assets/js/attrib';

export default {
  name: 'RestaurantPage',
  components: {CategorySelector},
  data () {
    return {
      restaurantType: RestaurantType
    };
  }
};
</script>

<style scoped>

</style>
