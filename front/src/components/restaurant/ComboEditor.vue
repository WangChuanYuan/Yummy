<template>
  <el-container>
    <el-main style="padding: 0">
      <h3>套餐编辑</h3>
      <hr/>
      <el-breadcrumb separator-class="el-icon-arrow-right" style="padding: 10px 0 50px 30px">
        <el-breadcrumb-item :to="{path: '/restaurantCenter'}">店家中心</el-breadcrumb-item>
        <el-breadcrumb-item :to="{path: '/restaurantCenter/combo'}">套餐管理</el-breadcrumb-item>
        <el-breadcrumb-item>套餐编辑</el-breadcrumb-item>
      </el-breadcrumb>
      <el-form :model="comboForm" :rules="comboRules" ref="comboForm">
        <el-container>
          <!-- 可选商品 -->
          <el-aside style="padding-left: 10px; padding-right: 50px; width: 30%">
            <el-form-item prop="items" label="套餐项">
              <el-checkbox-group v-model="comboForm.items" style="display: none"></el-checkbox-group>
            </el-form-item>
            <el-table ref="goodsTable" :data="goods" stripe height="500px" @selection-change="selectItem">
              <el-table-column type="selection" v-show="aim === 'add'"/>
              <el-table-column prop="name" label="单品名"/>
              <el-table-column prop="avatar" label="图像">
                <template slot-scope="scope">
                  <img :src="scope.row.avatar" style="height: 40px; width: 40px"/>
                </template>
              </el-table-column>
              <el-table-column prop="num" label="数量" width="200px">
                <template slot-scope="scope">
                  <el-input-number v-model="scope.row.num" :min="0" :precision="0" size="mini"></el-input-number>
                </template>
              </el-table-column>
              <el-table-column fixed type="expand">
                <template slot-scope="scope">
                  <el-form label-position="left" inline>
                    <el-form-item label="单价">
                      <span>{{scope.row.price}}</span>
                    </el-form-item>
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
          </el-aside>
          <el-main>
            <!-- 套餐信息 -->
            <el-row>
              <el-col>
                <el-form-item prop="avatar" label="图像">
                  <el-input v-model="comboForm.avatar" style="display: none"></el-input>
                  <el-upload class="avatar-uploader" action="mock" :multiple="false" :auto-upload="false"
                             :on-change="uploadAvatar" :accept="'image/*'">
                    <img v-if="comboForm.avatar" :src="comboForm.avatar">
                    <i v-else class="el-icon-plus"></i>
                  </el-upload>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="6">
                <el-form-item prop="name" label="名称">
                  <el-input v-model="comboForm.name" placeholder="名称"></el-input>
                </el-form-item>
              </el-col>
              <el-col :span="6" :offset="2">
                <el-form-item prop="price" label="套餐价">
                  <el-input-number v-model="comboForm.price" :min="0" size="medium"
                                   controls-position="right"></el-input-number>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-form-item prop="dates" label="供应日期">
                <el-col :span="8">
                  <el-radio v-model="supplyPeriod" label="short">短期</el-radio>
                  <el-radio v-model="supplyPeriod" label="medium">中期</el-radio>
                  <el-radio v-model="supplyPeriod" label="long">长期</el-radio>
                </el-col>
                <el-col :span="6">
                  <el-date-picker
                    v-model="comboForm.dates"
                    type="daterange"
                    value-format="yyyy-MM-dd"
                    range-separator="至"
                    start-placeholder="起始日期"
                    end-placeholder="结束日期"
                    placeholder="选择供应日期">
                  </el-date-picker>
                </el-col>
              </el-form-item>
            </el-row>
            <el-row>
              <el-col :span="6">
                <el-form-item prop="stock" label="库存">
                  <el-input-number v-model="comboForm.stock" :min="0" :precision="0" size="medium"
                                   controls-position="right"></el-input-number>
                </el-form-item>
              </el-col>
              <el-col :span="6" :offset="2">
                <el-form-item prop="dailySupply" label="日供">
                  <el-input-number v-model="comboForm.dailySupply" :min="0" :precision="0" size="medium"
                                   controls-position="right"></el-input-number>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="15">
                <el-form-item prop="description" label="描述">
                  <el-input type="textarea" :rows="3" v-model="comboForm.description" placeholder="描述"></el-input>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="6">
                <el-button type="primary" @click="submit('comboForm')">保存</el-button>
                <el-button type="plain" @click="reset('comboForm')">重置</el-button>
              </el-col>
            </el-row>
          </el-main>
        </el-container>
      </el-form>
    </el-main>
  </el-container>
</template>

<script>
import Api from '../../assets/js/api';
import {Code} from '../../assets/js/attrib';

export default {
  name: 'ComboEditor',
  props: {
    'cid': {
      type: Number,
      default: 0
    },
    'aim': {
      // add || modify
      type: String,
      default: 'add'
    }
  },
  watch: {
    supplyPeriod (choice) {
      let dates = [];
      let today = new Date();
      dates[0] = today.format('yyyy-MM-dd');
      if (choice === 'short') {
        today.setDate(today.getDate() + 7);
        dates[1] = today.format('yyyy-MM-dd');
      } else if (choice === 'medium') {
        today.setDate(today.getDate() + 30);
        dates[1] = today.format('yyyy-MM-dd');
      } else if (choice === 'long') {
        today.setDate(today.getDate() + 365);
        dates[1] = today.format('yyyy-MM-dd');
      }
      this.comboForm.dates = dates;
    }
  },
  mounted () {
    if (this.aim === 'add') {
      Api.get('/get_selling_goods', {'rid': sessionStorage.getItem('id')}).then((data) => {
        this.goods = data;
      }).catch(() => {});
    } else if (this.aim === 'modify') {
      Api.get('/get_combo', {'cid': this.cid}).then((data) => {
        if (data) {
          let saleInfo = data.saleInfo;
          this.comboForm.avatar = saleInfo.avatar;
          this.comboForm.name = saleInfo.name;
          this.comboForm.description = saleInfo.description;
          this.comboForm.price = saleInfo.price;
          this.comboForm.dailySupply = saleInfo.dailySupply;
          this.comboForm.stock = saleInfo.stock;
          let dates = [];
          dates[0] = saleInfo.startDate;
          dates[1] = saleInfo.endDate;
          this.comboForm.dates = dates;
        }
      }).catch(() => {});
      Api.get('/get_combo_goods', {'cid': this.cid}).then((data) => {
        if (data) {
          this.goods = data;
          this.comboForm.items = data;
          this.$refs.goodsTable.toggleAllSelection();
        }
      }).catch(() => {});
    }
  },
  data () {
    return {
      goods: [],
      avatarRaw: null,
      supplyPeriod: '',
      /** form */
      comboForm: {
        avatar: '',
        name: '',
        description: '',
        price: 0,
        dailySupply: 0,
        stock: 0,
        dates: [],
        items: []
      },
      comboRules: {
        avatar: [
          {
            required: true,
            message: '请上传套餐图片',
            trigger: 'change'
          }
        ],
        name: [
          {
            required: true,
            message: '请输入套餐名称',
            trigger: 'blur'
          }
        ],
        price: [
          {
            required: true,
            message: '请输入套餐价',
            trigger: 'blur'
          }
        ],
        stock: [
          {
            required: true,
            message: '请输入套餐库存',
            trigger: 'blur'
          }
        ],
        dailySupply: [
          {
            required: true,
            message: '请输入套餐日供量',
            trigger: 'blur'
          }
        ],
        dates: [
          {
            required: true,
            message: '请选择供应日期',
            trigger: 'blur'
          }
        ],
        items: [
          {
            required: true,
            message: '请选择套餐项',
            trigger: 'blur'
          }
        ]
      }
    };
  },
  methods: {
    reset (formName) {
      this.$refs[formName].resetFields();
    },
    submit (formName) {
      this.$refs[formName].validate(valid => {
        if (valid) {
          let formData = new FormData();
          if (this.avatarRaw) formData.append('avatar', this.avatarRaw);
          formData.append('name', this.comboForm.name);
          formData.append('description', this.comboForm.description);
          formData.append('price', this.comboForm.price);
          formData.append('stock', this.comboForm.stock);
          formData.append('dailySupply', this.comboForm.dailySupply);
          formData.append('startDate', this.comboForm.dates[0]);
          formData.append('endDate', this.comboForm.dates[1]);
          formData.append('itemsJson', JSON.stringify(this.comboForm.items));

          let url = '/add_combo';
          if (this.aim === 'add') {
            formData.append('restaurant', sessionStorage.getItem('id'));
          } else if (this.aim === 'modify') {
            url = '/modify_combo';
            formData.append('cid', this.cid);
          }
          Api.post(url, formData).then((data) => {
            if (data.code === Code.SUCCESS) {
              this.$message.success(data.msg);
              this.$router.push('/restaurantCenter/combo');
            } else this.$message.warning(data.msg);
          }).catch(() => {});
        }
      });
    },
    selectItem (items) {
      let totalPrice = 0;
      for (let index in items) {
        totalPrice += items[index].num * items[index].price;
      }
      this.comboForm.price = totalPrice;
      this.comboForm.items = items;
    },
    uploadAvatar (avatar) {
      this.comboForm.avatar = URL.createObjectURL(avatar.raw);
      this.avatarRaw = avatar.raw;
    }
  }
};
</script>

<style scoped>
  .avatar-uploader {
    border: 1px dashed #d9d9d9;
    width: 200px;
    height: 200px;
    cursor: pointer;
    overflow: hidden;
  }

  .avatar-uploader:hover {
    border-color: var(--theme-blue);
  }

  .avatar-uploader img {
    width: 200px;
    height: 200px;
    display: block;
  }

  .avatar-uploader i {
    font-size: 40px;
    color: #8c939d;
    width: 200px;
    height: 200px;
    line-height: 200px;
    text-align: center;
  }
</style>
