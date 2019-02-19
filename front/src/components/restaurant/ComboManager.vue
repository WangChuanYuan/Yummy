<template>
  <div>
    <h3>套餐管理</h3>
    <hr/>
    <el-container>
      <el-main>
        <el-row>
          <el-col :span="8" :offset="2">
            <el-button type="text" @click="add" style="float: left">新增套餐</el-button>
          </el-col>
        </el-row>
        <el-row>
          <el-col v-for="(item, index) in combos" :key="index" :span="9" :offset="2">
            <ComboCard/>
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
import ComboCard from './goods/ComboCard';

export default {
  name: 'ComboManager',
  components: {ComboCard},
  data () {
    return {
      combos: []
    };
  },
  mounted () {
    // TODO
  },
  methods: {
    add () {
      this.$router.push('/restaurantCenter/editCombo');
    }
  }
};
</script>

<style scoped>

</style>
