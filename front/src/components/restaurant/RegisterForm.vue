<template>
  <el-form :model="restaurantInfo" :rules="infoRules" ref="restaurantInfo" class="center" style="width: 30%">
    <el-form-item prop="id" label="注册码" v-show="aim !== 'add'">
      <el-input v-model="restaurantInfo.name" :readonly="true"></el-input>
    </el-form-item>
    <el-form-item prop="name" label="门店名称">
      <el-input v-model="restaurantInfo.name" placeholder="门店名称"></el-input>
    </el-form-item>
    <el-form-item prop="password" label="密码" v-show="aim === 'add'">
      <el-input v-model="restaurantInfo.password" placeholder="密码"></el-input>
    </el-form-item>
    <el-form-item prop="type" label="门店分类">
      <el-select v-model="restaurantInfo.registerInfo.type" placeholder="请选择" style="width: 100%" :value="types.DELICACY.value">
        <el-option v-for="type in types" :key="type.value" :value="type.value" :label="type.label"></el-option>
      </el-select>
    </el-form-item>
    <el-form-item prop="location" label="位置">
      <el-input v-model="restaurantInfo.registerInfo.location" placeholder="位置">
        <v-region slot="append" :ui="true" @values="locationChange"></v-region>
      </el-input>
    </el-form-item>
    <el-form-item prop="detailLocation" label="详细地址">
      <el-input v-model="restaurantInfo.registerInfo.detailLocation" placeholder="详细地址"></el-input>
    </el-form-item>
    <el-form-item>
      <el-button type="primary" @click="submit('restaurantInfo')" style="width: 100%">提交</el-button>
    </el-form-item>
  </el-form>
</template>

<script>
import {Code, RestaurantType} from '../../assets/js/attrib';
import Api from '../../assets/js/api';

export default {
  name: 'RegisterForm',
  props: {
    'aim': {
      // add || modify
      type: String,
      default: 'add'
    }
  },
  data () {
    return {
      types: RestaurantType,
      restaurantInfo: {
        id: '',
        password: '',
        registerInfo: {
          name: '',
          type: RestaurantType.DELICACY.value,
          location: '',
          detailLocation: ''
        }
      },
      infoRules: {
        name: [
          {
            required: true,
            message: '请输入门店名称',
            trigger: 'blur'
          }
        ],
        password: [
          {
            required: true,
            message: '请输入密码',
            trigger: 'blur'
          }
        ],
        type: [
          {
            required: true,
            message: '请选择门店分类',
            trigger: 'blur'
          }
        ],
        location: [
          {
            required: true,
            message: '请选择位置',
            trigger: 'blur'
          }
        ],
        detailLocation: [
          {
            required: true,
            message: '请输入详细地址',
            trigger: 'blur'
          }
        ]
      }
    };
  },
  mounted () {
    if (this.aim !== 'add') {
      this.init();
    }
  },
  methods: {
    locationChange (val) {
      let location = '';
      if (val.province) {
        location += val.province.value;
      }
      if (val.city) {
        location += val.city.value;
      }
      if (val.area) {
        location += val.area.value;
      }
      if (val.town) {
        location += val.town.value;
      }
      this.restaurantInfo.registerInfo.location = location;
    },
    init () {
      Api.post('/get_restaurant', {
        'id': sessionStorage.getItem('id')
      }).then((data) => {
        if (data) this.restaurantInfo = data;
      }).catch(() => {
      });
    },
    submit (formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          let url = '/register_restaurant';
          if (this.aim === 'add') {
            url = '/modify_restaurant';
          }
          Api.post(url, this.restaurantInfo).then((data) => {
            if (data.code === Code.SUCCESS) {
              if (this.aim === 'add') {
                let msg = '注册成功！请记住您的注册码:' + data.value.id;
                this.$alert(msg, '注册餐厅成功', {
                  confirmButtonText: '确定',
                  callback: action => {
                    this.$router.push('/');
                  }
                });
              } else if (this.aim === 'modify') {
                this.$alert('提交成功，经理审核通过后即可完成信息修改', '提交成功', {
                  confirmButtonText: '确定',
                  callback: action => {
                    this.init();
                  }
                });
              }
            } else this.$message.warning(data.msg);
          }).catch(() => {});
        }
      });
    }
  }
};
</script>

<style scoped>
</style>
