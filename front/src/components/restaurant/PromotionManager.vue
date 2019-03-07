<template>
  <div>
    <h3>优惠管理</h3>
    <hr/>
    <el-container>
      <el-header>
        <el-button type="text" style="float: left" @click="editing = true">新增优惠</el-button>
      </el-header>
      <el-main>
        <el-table :data="promotions" height="500px">
          <el-table-column prop="pid" label="id" width="50"/>
          <el-table-column prop="quotaRequired" label="消费额度" width="180">
            <template slot-scope="scope">
              <el-input type="number" v-model="scope.row.quotaRequired"></el-input>
            </template>
          </el-table-column>
          <el-table-column prop="quotaOffered" label="优惠额度" width="180">
            <template slot-scope="scope">
              <el-input type="number" v-model="scope.row.quotaOffered"></el-input>
            </template>
          </el-table-column>
          <el-table-column label="起始日期">
            <template slot-scope="scope">
              <el-date-picker
                v-model="scope.row.startDate"
                value-format="yyyy-MM-dd"
                placeholder="选择开始日期">
              </el-date-picker>
            </template>
          </el-table-column>
          <el-table-column label="截止日期">
            <template slot-scope="scope">
              <el-date-picker
                v-model="scope.row.endDate"
                value-format="yyyy-MM-dd"
                placeholder="选择截止日期">
              </el-date-picker>
            </template>
          </el-table-column>
          <el-table-column label="操作">
            <template slot-scope="scope">
              <el-button type="text" @click="modifyPromotion(scope.$index)">修改</el-button>
              <el-button type="text" @click="deletePromotion(scope.$index)">删除</el-button>
            </template>
          </el-table-column>
        </el-table>
        <el-dialog title="新增优惠"
                   :visible.sync="editing"
                   @open="promotionForm.quotaRequired = 0; promotionForm.quotaOffered = 0; promotionForm.dates = []"
                   width="25%">
          <el-form :model="promotionForm" ref="promotionForm">
            <el-form-item prop="quotaRequired" label="消费额度" :rules="[{required: true, message: '请输入消费额度', trigger: 'blur'}]">
              <el-input type="numebr" v-model="promotionForm.quotaRequired" placeholder="消费额度"></el-input>
            </el-form-item>
            <el-form-item prop="quotaOffered" label="优惠额度" :rules="[{required: true, message: '请输入优惠额度', trigger: 'blur'}]">
              <el-input type="number" v-model="promotionForm.quotaOffered" placeholder="优惠额度"></el-input>
            </el-form-item>
            <el-form-item prop="dates" label="活动日期" :rules="[{required: true, message: '请选择活动日期', trigger: 'blur'}]">
              <el-date-picker
                v-model="promotionForm.dates"
                type="daterange"
                value-format="yyyy-MM-dd"
                range-separator="至"
                start-placeholder="起始日期"
                end-placeholder="结束日期"
                placeholder="选择日期">
              </el-date-picker>
            </el-form-item>
            <el-form-item>
              <el-button type="primary" @click="addPromotion('promotionForm')">提交</el-button>
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
  name: 'PromotionManager',
  data () {
    return {
      editing: false,
      promotions: [],
      /** form */
      promotionForm: {
        quotaRequired: 0,
        quotaOffered: 0,
        dates: []
      }
    };
  },
  mounted () {
    Api.get('/get_promotions', {restaurant: sessionStorage.getItem('id')}).then((data) => {
      if (data) {
        this.promotions = data;
      }
    }).catch(() => {});
  },
  methods: {
    addPromotion (formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          Api.post('/add_promotion', {
            promotion: {
              quotaRequired: this.promotionForm.quotaRequired,
              quotaOffered: this.promotionForm.quotaOffered,
              startDate: this.promotionForm.dates[0],
              endDate: this.promotionForm.dates[1]
            },
            restaurant: sessionStorage.getItem('id')
          }).then((data) => {
            if (data.code === Code.SUCCESS) {
              this.$message.success(data.msg);
              this.promotions.push(data.value);
            } else this.$message.warning(data.msg);
            this.editing = false;
          }).catch(() => {
            this.editing = false;
          });
        }
      });
    },
    modifyPromotion (index) {
      let promotion = this.promotions[index];
      if (promotion.quotaRequired && promotion.quotaOffered && promotion.startDate && promotion.endDate) {
        Api.post('/modify_promotion', {
          promotion: promotion,
          restaurant: sessionStorage.getItem('id')
        }).then((data) => {
          if (data.code === Code.SUCCESS) {
            this.$message.success(data.msg);
          } else this.$message.warning(data.msg);
          this.editing = false;
        }).catch(() => {
          this.editing = false;
        });
      } else this.$message.warning('请完整优惠信息');
    },
    deletePromotion (index) {
      this.$confirm('确定删除优惠？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        Api.post('/delete_promotion', {
          'pid': this.promotions[index].pid
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
