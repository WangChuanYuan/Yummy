<template>
  <div>
    <h3>单品管理</h3>
    <hr/>
    <el-container>
      <el-main>
        <el-row>
          <el-col :span="8" :offset="2">
            <el-button type="text" @click="add" style="float: left">新增单品</el-button>
          </el-col>
        </el-row>
        <el-row>
          <el-col v-for="(item, index) in goods" :key="index" :span="9" :offset="2">
            <GoodsCard/>
          </el-col>
        </el-row>
        <el-pagination
          background
          layout="prev, pager, next"
          :page-size="8"
          :total="100"
          style="padding-top: 10px">
        </el-pagination>
      </el-main>
      <el-aside></el-aside>
    </el-container>
  </div>
</template>

<script>
import GoodsCard from './goods/GoodsCard';

export default {
  name: 'GoodsManager',
  components: {GoodsCard},
  data () {
    return {
      goods: []
    };
  },
  mounted () {
    // TODO
  },
  methods: {
    add () {
      this.$router.push('/restaurantCenter/editGoods');
    }
  }
};
</script>

<style scoped>

</style>
