<template>
  <div>
    <h3>地址管理</h3>
    <hr/>
    <el-button type="text" @click="editing = true">新增地址</el-button>
    <el-row>
      <el-col v-for="(address, index) in addresses" :key="index" :span="8" :offset="1">
        <address-card :address="address"/>
      </el-col>
    </el-row>
    <!-- 地址编辑 -->
    <el-dialog title="编辑地址" :visible.sync="editing" width="30%" center>
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
        <el-form-item style="margin-left: 30%">
          <el-button type="primary" @click="addAddress('addressForm')">保存</el-button>
          <el-button type="plain" @click="editing = false">取消</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>
  </div>
</template>

<script>
import AddressCard from './AddressCard';

var phoneRule = (rule, value, callback) => {
  if (!value) {
    callback(new Error('请输入手机号'));
  } else if (!/^1[3|4|5|7|8][0-9]\d{8}$/.test(value)) {
    callback(new Error('请输入正确的11位手机号'));
  } else {
    callback();
  }
};

export default {
  name: 'MemberAddress',
  components: {AddressCard},
  data () {
    return {
      addresses: [],
      editing: false,
      addressForm: {
        name: '',
        sex: '',
        location: '',
        detailLocation: '',
        phone: ''
      },
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
  methods: {
    addAddress (formName) {
    }
  }
};
</script>

<style scoped>

</style>
