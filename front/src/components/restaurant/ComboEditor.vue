<template>
  <el-container>
    <el-main style="padding: 0">
      <h3>套餐编辑</h3>
      <hr/>
      <el-breadcrumb separator-class="el-icon-arrow-right" style="padding: 10px 0 50px 30px">
        <el-breadcrumb-item :to="{ path: '/restaurantCenter' }">店家中心</el-breadcrumb-item>
        <el-breadcrumb-item :to="{path: '/restaurantCenter/combo'}">套餐管理</el-breadcrumb-item>
        <el-breadcrumb-item>套餐编辑</el-breadcrumb-item>
      </el-breadcrumb>
      <el-container>
        <!-- 可选商品 -->
        <el-aside width="30%">
          <el-table :data="goods" stripe height="500px" @selection-change="selectItem">
            <el-table-column type="selection"/>
            <el-table-column prop="name" label="单品名"/>
            <el-table-column prop="avatar" label="图像">
              <template slot-scope="scope">
                <img :src="scope.row.avatar" style="height: 40px; width: 40px"/>
              </template>
            </el-table-column>
            <el-table-column prop="num" label="包含数量" width="200px">
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
          <!-- 套餐表单 -->
          <el-form :model="comboForm" :rules="comboRules" ref="comboForm" style="padding-left: 50px">
            <el-row>
              <el-col>
                <el-form-item prop="avatar" label="图像">
                  <el-input v-model="comboForm.avatar" style="display: none"></el-input>
                  <el-upload class="avatar-uploader" action="mock" :multiple="false" :auto-upload="false"
                             :on-change="uploadAvatar" :accept="'image/*'">
                    <img v-if="comboForm.avatar" :src="goodsForm.avatar">
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
                <el-form-item prop="price" label="单价">
                  <el-input-number v-model="comboForm.price" :min="0" size="medium"
                                   controls-position="right"></el-input-number>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-form-item prop="dates" label="供应日期">
                <el-col :span="7">
                  <el-radio v-model="supplyPeriod" label="short">短期</el-radio>
                  <el-radio v-model="supplyPeriod" label="medium">中期</el-radio>
                  <el-radio v-model="supplyPeriod" label="long">长期</el-radio>
                </el-col>
                <el-col :span="5">
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
          </el-form>
        </el-main>
      </el-container>
    </el-main>
  </el-container>
</template>

<script>
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
    supplyPeriod (val) {
      let dates = [];
      let today = new Date();
      dates[0] = today.format('yyyy-MM-dd');
      if (val === 'short') {
        today.setDate(today.getDate() + 7);
        dates[1] = today.format('yyyy-MM-dd');
      } else if (val === 'medium') {
        today.setDate(today.getDate() + 30);
        dates[1] = today.format('yyyy-MM-dd');
      } else if (val === 'long') {
        today.setDate(today.getDate() + 365);
        dates[1] = today.format('yyyy-MM-dd');
      }
      this.comboForm.dates = dates;
    }
  },
  mounted () {
    if (this.aim === 'modify') {
      // TODO
    }
  },
  data () {
    return {
      goods: [],
      selectedItem: [],
      avatarRaw: null,
      supplyPeriod: '',
      comboForm: {
        avatar: '',
        name: '',
        description: '',
        price: 0,
        dailySupply: 0,
        stock: 0,
        dates: []
      },
      comboRules: {
        avatar: [
          {
            required: true,
            message: '请上传商品图片',
            trigger: 'change'
          }
        ],
        name: [
          {
            required: true,
            message: '请输入商品名称',
            trigger: 'blur'
          }
        ],
        price: [
          {
            required: true,
            message: '请输入商品单价',
            trigger: 'blur'
          }
        ],
        stock: [
          {
            required: true,
            message: '请输入商品库存',
            trigger: 'blur'
          }
        ],
        dailySupply: [
          {
            required: true,
            message: '请输入商品日供量',
            trigger: 'blur'
          }
        ],
        dates: [
          {
            required: true,
            message: '请选择供应日期',
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
      if (this.$refs[formName].validate()) {
      }
    },
    selectItem (val) {
      this.selectItem = val;
    },
    uploadAvatar (avatar) {
      this.goodsForm.avatar = URL.createObjectURL(avatar.raw);
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
