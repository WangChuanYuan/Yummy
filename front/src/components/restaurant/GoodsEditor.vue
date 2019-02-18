<template>
  <el-container>
    <el-main style="padding: 0">
      <h3>商品编辑</h3>
      <hr/>
      <el-breadcrumb separator-class="el-icon-arrow-right" style="padding: 10px 0 50px 30px">
        <el-breadcrumb-item :to="{ path: '/restaurantCenter' }">店家中心</el-breadcrumb-item>
        <el-breadcrumb-item :to="{path: '/restaurantCenter/goods'}">单品管理</el-breadcrumb-item>
        <el-breadcrumb-item>商品编辑</el-breadcrumb-item>
      </el-breadcrumb>
      <el-form :model="goodsForm" :rules="goodsRules" ref="goodsForm" style="padding-left: 2%">
        <el-row>
          <el-col>
            <el-form-item prop="avatar" label="图像">
              <el-input v-model="goodsForm.avatar" style="display: none"></el-input>
              <el-upload class="avatar-uploader" action="mock" :multiple="false" :auto-upload="false"
                         :on-change="uploadAvatar" :accept="'image/*'">
                <img v-if="goodsForm.avatar" :src="goodsForm.avatar">
                <i v-else class="el-icon-plus"></i>
              </el-upload>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="6">
            <el-form-item prop="name" label="名称">
              <el-input v-model="goodsForm.name" placeholder="名称"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="6" :offset="2">
            <el-form-item prop="price" label="单价">
              <el-input-number v-model="goodsForm.price" :min="0" size="medium"
                               controls-position="right"></el-input-number>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-form-item prop="dates" label="供应日期">
            <el-col :span="6">
              <el-radio v-model="supplyPeriod" label="short">短期</el-radio>
              <el-radio v-model="supplyPeriod" label="medium">中期</el-radio>
              <el-radio v-model="supplyPeriod" label="long">长期</el-radio>
            </el-col>
            <el-col :span="5">
              <el-date-picker
                v-model="goodsForm.dates"
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
              <el-input-number v-model="goodsForm.stock" :min="0" :precision="0" size="medium"
                               controls-position="right"></el-input-number>
            </el-form-item>
          </el-col>
          <el-col :span="6" :offset="2">
            <el-form-item prop="dailySupply" label="日供">
              <el-input-number v-model="goodsForm.dailySupply" :min="0" :precision="0" size="medium"
                               controls-position="right"></el-input-number>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="15">
            <el-form-item prop="description" label="描述">
              <el-input type="textarea" :rows="3" v-model="goodsForm.description" placeholder="描述"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="5" :offset="4">
            <el-button type="primary" @click="submit('goodsForm')">保存</el-button>
            <el-button type="plain" @click="reset('goodsForm')">重置</el-button>
          </el-col>
        </el-row>
      </el-form>
    </el-main>
  </el-container>
</template>

<script>

export default {
  name: 'GoodsEditor',
  props: {
    'gid': {
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
      this.goodsForm.dates = dates;
    }
  },
  mounted () {
    if (this.aim === 'modify') {
      // TODO
    }
  },
  data () {
    return {
      avatarRaw: null,
      supplyPeriod: '',
      goodsForm: {
        avatar: '',
        name: '',
        description: '',
        price: 0,
        dailySupply: 0,
        stock: 0,
        dates: []
      },
      goodsRules: {
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
