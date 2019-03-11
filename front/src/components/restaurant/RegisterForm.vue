<template>
  <el-form :model="registerInfo" :rules="infoRules" ref="registerInfo" class="center" style="width: 30%">
    <el-form-item prop="id" label="注册码" v-show="aim !== 'add'">
      <el-input v-model="registerInfo.id" :readonly="true"/>
    </el-form-item>
    <el-form-item prop="name" label="门店名称">
      <el-input v-model="registerInfo.name" placeholder="门店名称"/>
    </el-form-item>
    <el-form-item prop="password" label="密码" v-show="aim === 'add'">
      <el-input v-model="registerInfo.password" type="password" placeholder="密码"/>
    </el-form-item>
    <el-form-item prop="email" label="门店邮箱">
      <el-input v-model="registerInfo.email" type="email" placeholder="邮箱"/>
    </el-form-item>
    <el-form-item prop="type" label="门店分类">
      <el-select v-model="registerInfo.type" placeholder="请选择" style="width: 100%" :value="types.DELICACY.value">
        <el-option v-for="type in types" :key="type.value" :value="type.value" :label="type.label"/>
      </el-select>
    </el-form-item>
    <el-form-item prop="location" label="位置">
      <v-region :ui="true" @values="locationChange"></v-region>
      <el-input v-model="registerInfo.location" placeholder="位置" :readonly="true"></el-input>
    </el-form-item>
    <el-form-item prop="detailLocation" label="详细地址">
      <el-input v-model="registerInfo.detailLocation" placeholder="详细地址" :readonly="true"></el-input>
      <Map :center="registerInfo.location" @locate="detailLocation"/>
    </el-form-item>
    <el-form-item>
      <el-button type="primary" @click="submit('registerInfo')" style="width: 100%">提交</el-button>
    </el-form-item>
  </el-form>
</template>

<script>
import {Code, RestaurantType} from '../../assets/js/attrib';
import Api from '../../assets/js/api';
import Map from '../Map';

export default {
  name: 'RegisterForm',
  components: {Map},
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
      /** form */
      registerInfo: {
        id: '',
        name: '',
        email: '',
        password: '',
        type: RestaurantType.DELICACY.value,
        location: '',
        detailLocation: '',
        lng: 0,
        lat: 0
      },
      infoRules: {
        name: [
          {
            required: true,
            message: '请输入门店名称',
            trigger: 'blur'
          }
        ],
        email: [
          {
            required: true,
            message: '请输入门店邮箱',
            trigger: 'blur'
          },
          {
            type: 'email',
            message: '请输入正确的邮箱地址',
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
    if (this.aim === 'modify') {
      this.init();
    }
  },
  methods: {
    detailLocation (poi) {
      this.registerInfo.detailLocation = poi.address;
      this.registerInfo.lng = poi.point.lng;
      this.registerInfo.lat = poi.point.lat;
    },
    locationChange (val) {
      let location = '';
      if (val.province) {
        location += val.province.value;
      }
      if (val.city) {
        location += ',';
        location += val.city.value;
      }
      if (val.area) {
        location += ',';
        location += val.area.value;
      }
      if (val.town) {
        location += ',';
        location += val.town.value;
      }
      this.registerInfo.location = location;
    },
    init () {
      Api.get('/get_restaurant', {
        'id': sessionStorage.getItem('id')
      }).then((data) => {
        if (data) {
          this.registerInfo = data.registerInfo;
          this.registerInfo.id = data.id;
          this.registerInfo.password = data.password;
        }
      }).catch(() => {
      });
    },
    submit (formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          let url = '/register_restaurant';
          if (this.aim !== 'add') {
            url = '/modify_register_info';
          }
          Api.post(url, this.registerInfo).then((data) => {
            if (data.code === Code.SUCCESS) {
              if (this.aim === 'add') {
                let msg = '申请成功，申请结果将在审核通过后邮件通知\n请记住您的注册码:' + data.value.id;
                this.$alert(msg, '注册申请成功', {
                  confirmButtonText: '确定',
                  callback: action => {
                    this.$router.push('/');
                  }
                });
              } else if (this.aim === 'modify') {
                this.$alert('提交成功，修改结果将在审核后通过邮件通知', '提交成功', {
                  confirmButtonText: '确定',
                  callback: action => {
                    this.registerInfo = data.value.registerInfo;
                    this.registerInfo.id = data.value.id;
                    this.registerInfo.password = data.value.password;
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
