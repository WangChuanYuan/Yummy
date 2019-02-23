<template>
  <el-form :model="addressForm" :rules="addressRules" ref="addressForm">
    <el-form-item prop="name" label="姓名">
      <el-input v-model="addressForm.name" placeholder="姓名"></el-input>
    </el-form-item>
    <el-form-item prop="sex" label="性别">
      <el-radio v-model="addressForm.sex" label="MAN">先生</el-radio>
      <el-radio v-model="addressForm.sex" label="WOMAN">女士</el-radio>
    </el-form-item>
    <el-form-item prop="location" label="位置">
      <v-region :ui="true" @values="locationChange"></v-region>
      <el-input v-model="addressForm.location" placeholder="位置" :readonly="true"></el-input>
    </el-form-item>
    <el-form-item prop="detailLocation" label="详细地址">
      <el-input v-model="addressForm.detailLocation" placeholder="详细地址" :readonly="true"></el-input>
      <Map :center="addressForm.location" @locate="detailLocation"/>
    </el-form-item>
    <el-form-item prop="phone" label="手机号">
      <el-input v-model="addressForm.phone" placeholder="手机号"></el-input>
    </el-form-item>
    <div style="margin-left: 30%">
      <el-button type="primary" @click="submit('addressForm')">保存</el-button>
      <el-button type="plain" @click="reset('addressForm')">重置</el-button>
    </div>
  </el-form>
</template>

<script>
import {Sex} from '../../../assets/js/attrib';
import Map from '../../Map';

export default {
  name: 'AddressEditor',
  components: {Map},
  props: {
    'address': {
      type: Object,
      default: function () {
        return {
          aid: 0,
          name: '',
          sex: Sex.MAN,
          location: '',
          detailLocation: '',
          lng: 0,
          lat: 0,
          phone: ''
        };
      }
    },
    'aim': {
      // add || modify
      type: String,
      default: 'add'
    }
  },
  data () {
    return {
      addressForm: JSON.parse(JSON.stringify(this.address)),
      addressRules: {
        name: [
          {
            required: true,
            message: '请输入姓名',
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
            message: '请选择详细地址',
            trigger: 'blur'
          }
        ],
        phone: [
          {
            required: true,
            message: '请输入手机号',
            trigger: 'blur'
          },
          {
            pattern: /^1[3|4|5|7|8][0-9]\d{8}$/,
            message: '请输入正确的11位手机号',
            trigger: 'blur'
          }
        ]
      }
    };
  },
  watch: {
    address (val) {
      this.addressForm = JSON.parse(JSON.stringify(val));
    }
  },
  methods: {
    detailLocation (poi) {
      this.addressForm.detailLocation = poi.address;
      this.addressForm.lng = poi.point.lng;
      this.addressForm.lat = poi.point.lat;
    },
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
      this.addressForm.location = location;
    },
    reset (formName) {
      this.$refs[formName].resetFields();
    },
    submit (formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          let callback = 'add';
          if (this.aim === 'modify') {
            callback = 'modify';
          }
          this.$emit(callback, this.addressForm);
        }
      });
    }
  }
};
</script>

<style scoped>

</style>
