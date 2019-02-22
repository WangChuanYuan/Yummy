<template>
  <div>
    <h3>分类管理</h3>
    <hr/>
    <el-container>
      <el-header>
        <el-button type="text" style="float: left" @click="editing = true">新增分类</el-button>
      </el-header>
      <el-main>
        <el-table :data="categories" height="500px">
          <el-table-column type="index"/>
          <el-table-column prop="cgid" label="id"/>
          <el-table-column prop="name" label="名称">
            <template slot-scope="scope">
              <el-input v-model="scope.row.name"></el-input>
            </template>
          </el-table-column>
          <el-table-column prop="description" label="描述">
            <template slot-scope="scope">
              <el-input v-model="scope.row.description"></el-input>
            </template>
          </el-table-column>
          <el-table-column label="操作">
            <template slot-scope="scope">
              <el-button type="text" @click="modifyCategory(scope.$index)">修改</el-button>
              <el-button type="text" @click="deleteCategory(scope.$index)">删除</el-button>
            </template>
          </el-table-column>
        </el-table>
        <el-dialog title="新增分类" :visible.sync="editing" @open="categoryForm.name = ''; categoryForm.description = ''" width="25%">
          <el-form :model="categoryForm" ref="categoryForm">
            <el-form-item prop="name" label="名称" :rules="[{required: true, message: '请输入名称', trigger: 'blur'}]">
              <el-input v-model="categoryForm.name" placeholder="分类名称"></el-input>
            </el-form-item>
            <el-form-item prop="description" label="描述">
              <el-input type="textarea" :rows="3" v-model="categoryForm.description" placeholder="分类描述"></el-input>
            </el-form-item>
            <el-form-item>
              <el-button type="primary" @click="addCategory('categoryForm')">提交</el-button>
            </el-form-item>
          </el-form>
        </el-dialog>
      </el-main>
    </el-container>
  </div>
</template>

<script>
import Api from '../../assets/js/api';
import {Code} from '../../assets/js/attrib';

export default {
  name: 'CategoryManager',
  data () {
    return {
      editing: false,
      categories: [],
      /** form */
      categoryForm: {
        name: '',
        description: ''
      }
    };
  },
  mounted () {
    Api.get('/get_categories', {restaurant: sessionStorage.getItem('id')}).then((data) => {
      if (data) {
        this.categories = data;
      }
    }).catch(() => {});
  },
  methods: {
    addCategory (formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          Api.post('/add_category', {
            category: this.categoryForm,
            restaurant: sessionStorage.getItem('id')
          }).then((data) => {
            if (data.code === Code.SUCCESS) {
              this.$message.success(data.msg);
              this.categories.push(data.value);
            } else this.$message.warning(data.msg);
            this.editing = false;
          }).catch(() => {
            this.editing = false;
          });
        }
      });
    },
    modifyCategory (index) {
      let category = this.categories[index];
      if (category.name) {
        Api.post('/modify_category', {
          category: category,
          restaurant: sessionStorage.getItem('id')
        }).then((data) => {
          if (data.code === Code.SUCCESS) {
            this.$message.success(data.msg);
          } else this.$message.warning(data.msg);
          this.editing = false;
        }).catch(() => {
          this.editing = false;
        });
      } else this.$message.warning('请填写分类名称');
    },
    deleteCategory (index) {
      this.$confirm('删除分类将删除分类下所有商品，确定删除？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        Api.post('/delete_category', {
          'cgid': this.categories[index].cgid
        }).then((data) => {
          if (data.code === Code.SUCCESS) {
            this.categories.splice(index, 1);
          } else {
            this.$message.warning(data.msg);
          }
        });
      }).catch(() => {});
    }
  }
};
</script>

<style scoped>

</style>
