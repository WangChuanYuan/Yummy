<template>
  <div>
    <h3>套餐管理</h3>
    <hr/>
    <el-container>
      <el-main>
        <el-row>
          <el-col :span="8" :offset="2">
            <el-button type="text" @click="addCombo" style="float: left">新增套餐</el-button>
          </el-col>
        </el-row>
        <el-row>
          <el-col v-for="(item, index) in combos" :key="index" :span="9" :offset="2">
            <ComboCard :combo="item" @delete="deleteCombo(index)"/>
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
import ComboCard from '../goods/ComboCard';
import Api from '../../assets/js/api';
import {Code} from '../../assets/js/attrib';

export default {
  name: 'ComboManager',
  components: {ComboCard},
  data () {
    return {
      combos: [],
      currentPage: 1,
      pageSize: 6,
      total: 100
    };
  },
  mounted () {
    this.getCombosInPage(this.currentPage, this.pageSize);
  },
  methods: {
    getCombosInPage (page, size) {
      Api.get('/get_selling_combos', {
        rid: sessionStorage.getItem('id'),
        page: page,
        size: size
      }).then((data) => {
        if (data) this.combos = data;
      }).catch(() => {});
    },
    addCombo () {
      this.$router.push('/restaurantCenter/editCombo');
    },
    deleteCombo (index) {
      this.$confirm('确认下架该套餐？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        Api.post('/delete_combo', {
          'cid': this.combos[index].cid
        }).then((data) => {
          if (data.code === Code.SUCCESS) {
            this.addresses.splice(index, 1);
          } else {
            this.$message.warning(data.msg);
          }
        });
      }).catch(() => {});
    },
    pageChange (page) {
      this.currentPage = page;
      this.getCombosInPage(this.currentPage, this.pageSize);
    }
  }
};
</script>

<style scoped>

</style>
