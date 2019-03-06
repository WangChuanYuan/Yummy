<template>
  <div>
    <h3>消费统计</h3>
    <hr/>
    <el-date-picker
      v-model="dates"
      type="daterange"
      value-format="yyyy-MM-dd"
      range-separator="至"
      start-placeholder="起始日期"
      end-placeholder="结束日期"
      placeholder="选择日期">
    </el-date-picker>
    <el-select v-model="restaurantType" clearable placeholder="选择餐厅类型" :value="types.DELICACY.value">
      <el-option v-for="type in types" :key="type.value" :value="type.value" :label="type.label"/>
    </el-select>
  </div>
</template>

<script>
import {RestaurantType} from '../../../assets/js/attrib';

export default {
  name: 'MemberConsume',
  data () {
    return {
      types: RestaurantType,
      /* conditions */
      dates: [],
      restaurantType: ''
    };
  }
};
</script>

<style scoped>

</style>
