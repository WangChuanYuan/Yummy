<template>
  <el-row>
    <el-col v-for="(item, index) in goods" :key="index" :span="8" :offset="1">
      <GoodsCard/>
    </el-col>
  </el-row>
</template>

<script>
import GoodsCard from './goods/GoodsCard';

export default {
  name: 'GoodsManager',
  components: {GoodsCard},
  data () {
    return {
      goods: [
        {}
      ]
    };
  }
};
</script>

<style scoped>

</style>
