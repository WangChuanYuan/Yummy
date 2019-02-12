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
      <el-input v-model="addressForm.location" placeholder="位置"></el-input>
    </el-form-item>
    <el-form-item prop="detailLocation" label="详细地址">
      <el-input v-model="addressForm.detailLocation" placeholder="详细地址"></el-input>
    </el-form-item>
    <el-form-item prop="phone" label="手机号">
      <el-input v-model="addressForm.phone" placeholder="手机号"></el-input>
    </el-form-item>
    <div style="margin-left: 30%">
      <el-button type="primary" @click="saveAddress('addressForm')">保存</el-button>
      <el-button type="plain" @click="reset('addressForm')">重置</el-button>
    </div>
  </el-form>
</template>

<script>
import Api from '../../../assets/js/api';
import {Code, Sex} from '../../../assets/js/util';

const phoneRule = (rule, value, callback) => {
  if (!value) {
    callback(new Error('请输入手机号'));
  } else if (!/^1[3|4|5|7|8][0-9]\d{8}$/.test(value)) {
    callback(new Error('请输入正确的11位手机号'));
  } else {
    callback();
  }
};

export default {
  name: 'AddressEditor',
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
          phone: ''
        };
      }
    },
    'aim': {
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
            message: '请输入位置',
            trigger: 'blur'
          }
        ],
        detailLocation: [
          {
            required: true,
            message: '请输入详细地址',
            trigger: 'blur'
          }
        ],
        phone: [
          {
            required: true,
            trigger: 'blur',
            validator: phoneRule
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
    closeEditor () {
      this.$emit('closeEditor');
    },
    reset (formName) {
      this.$refs[formName].resetFields();
    },
    saveAddress (formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          let url = '/add_address';
          let callback = 'addAddress';
          if (this.aim === 'modify') {
            url = '/modify_address';
            callback = 'modifyAddress';
          }
          Api(url, {
            'address': this.addressForm,
            'email': sessionStorage.getItem('email')
          }).then((data) => {
            if (data.code === Code.SUCCESS) {
              this.$emit(callback, data.value);
            } else {
              this.$message.warning(data.msg);
            }
            this.closeEditor();
          }).catch(() => {
            this.closeEditor();
          });
        }
      });
    }
  }
};
</script>

<style scoped>

</style>
