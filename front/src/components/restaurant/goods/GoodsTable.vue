<template>
  <el-table :data="items" stripe :height="height">
    <el-table-column prop="name" label="单品名"/>
    <el-table-column prop="avatar" label="图像">
      <template slot-scope="scope">
        <img :src="scope.row.avatar" style="height: 40px; width: 40px"/>
      </template>
    </el-table-column>
    <el-table-column prop="num" label="包含数量"/>
    <el-table-column prop="price" label="单价"/>
    <el-table-column type="expand">
      <template slot-scope="scope">
        <el-form label-position="left" inline>
          <el-form-item label="库存">
            <span>{{scope.row.stock}}</span>
          </el-form-item>
          <el-form-item label="日供应">
            <span>{{scope.row.dailySupply}}</span>
          </el-form-item>
          <el-form-item label="起始日期">
            <span>{{scope.row.startDate}}</span>
          </el-form-item>
          <el-form-item label="结束日期">
            <span>{{scope.row.endDate}}</span>
          </el-form-item>
          <el-form-item label="描述">
            <span>{{scope.row.description}}</span>
          </el-form-item>
        </el-form>
      </template>
    </el-table-column>
  </el-table>
</template>

<script>
export default {
  name: 'GoodsTable',
  props: {
    goods: {
      type: Array,
      default: function () {
        return [];
      }
    },
    height: {
      type: String,
      default: '300px'
    }
  },
  watch: {
    goods (val) {
      this.items = JSON.parse(JSON.stringify(val));
    }
  },
  data () {
    return {
      items: JSON.parse(JSON.stringify(this.goods))
    };
  }
};
</script>

<style scoped>
</style>
