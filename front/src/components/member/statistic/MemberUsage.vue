<template>
  <div>
    <h3>订单统计</h3>
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
    <LinearChart/>
  </div>
</template>

<script>
import LinearChart from '../../charts/LinearChart';

export default {
  name: 'MemberStatistic',
  components: {LinearChart},
  data () {
    return {
      dates: []
    };
  }
};
</script>

<style scoped>

</style>
