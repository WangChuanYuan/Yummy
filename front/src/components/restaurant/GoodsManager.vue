<template>
  <div>
    <h3>单品管理</h3>
    <hr/>
    <el-container>
      <el-main>
        <el-row>
          <el-col :span="8" :offset="2">
            <el-button type="text" @click="addGoods" style="float: left">新增单品</el-button>
          </el-col>
        </el-row>
        <el-row>
          <el-col v-for="(item, index) in goods" :key="index" :span="9" :offset="2">
            <GoodsCard :goods="item" @delete="deleteGoods(index)"/>
          </el-col>
        </el-row>
        <el-pagination
          background
          layout="prev, pager, next"
          :page-size="pageSize"
          :total="total"
          style="padding-top: 10px"
          :current-page="currentPage"
          @current-change="pageChange">
        </el-pagination>
      </el-main>
      <el-aside></el-aside>
    </el-container>
  </div>
</template>

<script>
import GoodsCard from '../goods/GoodsCard';
import Api from '../../assets/js/api';
import {Code} from '../../assets/js/attrib';

export default {
  name: 'GoodsManager',
  components: {GoodsCard},
  data () {
    return {
      goods: [],
      currentPage: 1,
      pageSize: 6,
      total: 100
    };
  },
  mounted () {
    this.getGoodsInPage(this.currentPage, this.pageSize);
  },
  methods: {
    getGoodsInPage (page, size) {
      Api.get('/get_selling_goods', {
        rid: sessionStorage.getItem('id'),
        page: page,
        size: size
      }).then((data) => {
        if (data) this.goods = data;
      }).catch(() => {});
    },
    addGoods () {
      this.$router.push('/restaurantCenter/editGoods');
    },
    deleteGoods (index) {
      this.$confirm('确认下架该商品？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        Api.post('/delete_goods', {
          'gid': this.goods[index].gid
        }).then((data) => {
          if (data.code === Code.SUCCESS) {
            this.goods.splice(index, 1);
          } else {
            this.$message.warning(data.msg);
          }
        });
      }).catch(() => {});
    },
    pageChange (page) {
      this.currentPage = page;
      this.getGoodsInPage(this.currentPage, this.pageSize);
    }
  }
};
</script>

<style scoped>

</style>
