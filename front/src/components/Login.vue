<template>
  <div>
    <el-form :model="loginForm" ref="loginForm" class="center" style="width: 45%">
      <el-form-item prop="id" :label="idLabel" :rules="idRule">
        <el-input v-model="loginForm.id" :placeholder="idLabel"></el-input>
      </el-form-item>
      <el-form-item prop="password" label="密码" :rules="passwordRule">
        <el-input v-model="loginForm.password" type="password" placeholder="密码"></el-input>
      </el-form-item>
      <el-form-item>
        <el-radio v-model="role" label="MEMBER">我是会员</el-radio>
        <el-radio v-model="role" label="RESTAURANT">我是商家</el-radio>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="login('loginForm')">登录</el-button>
        <router-link to="/register">
          <el-button>注册</el-button>
        </router-link>
        <el-checkbox v-model="rememberMe" style="left: 30px">记住我</el-checkbox>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import Api from '../assets/js/api';
import {Code, Role} from '../assets/js/attrib';

const emailRule = [
  {
    required: true,
    message: '请输入用户邮箱',
    trigger: 'blur'
  },
  {
    type: 'email',
    message: '请输入正确的邮箱地址',
    trigger: ['blur', 'change']
  }
];

const codeRule = [
  {
    required: true,
    message: '请输入用户邮箱',
    trigger: 'blur'
  },
  {
    min: 7,
    max: 7,
    message: '注册码长度为7',
    trigger: ['blur', 'change']
  }
];

export default {
  name: 'Login',
  data () {
    return {
      idLabel: '邮箱',
      rememberMe: false,
      role: Role.MEMBER,
      loginForm: {
        id: null,
        password: null
      },
      idRule: emailRule,
      passwordRule: [
        {
          required: true,
          message: '请输入密码',
          trigger: 'blur'
        }
      ]
    };
  },
  mounted () {
    this.getCookies();
  },
  watch: {
    role (val) {
      if (val === Role.MEMBER) {
        this.idLabel = '邮箱';
        this.idRule = emailRule;
      } else if (val === Role.RESTAURANT) {
        this.idLabel = '注册码';
        this.idRule = codeRule;
      }
    }
  },
  methods: {
    getCookies () {
      if (this.$cookies.isKey('id')) {
        this.loginForm.id = this.$cookies.get('id');
        this.rememberMe = true;
        if (/^[A-Za-z\d]+([-_.][A-Za-z\d]+)*@([A-Za-z\d]+[-.])+[A-Za-z\d]{2,4}$/.test(this.$cookies.get('id'))) {
          this.role = Role.MEMBER;
        } else this.role = Role.RESTAURANT;
      }
    },
    login: function (formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          if (this.rememberMe === true) {
            if (!this.$cookies.isKey('id') || !(this.$cookies.get('id') === this.loginForm.id)) {
              this.$cookies.set('id', this.loginForm.id, 30 * 24 * 60 * 60);
            }
          } else {
            this.$cookies.remove('id');
          }
          Api('/login', {
            'id': this.loginForm.id,
            'password': this.loginForm.password,
            'role': this.role
          }).then((data) => {
            if (data.code === Code.SUCCESS) {
              let user = data.value;
              if (sessionStorage.getItem('id') !== user.id) {
                sessionStorage.clear();
                sessionStorage.setItem('id', user.id);
                sessionStorage.setItem('user', JSON.stringify(user));
              }
              if (user.role === Role.MEMBER) {
                this.$router.push('/memberCenter');
              } else {
                // todo with restaurant
              }
            } else {
              this.$message.warning(data.msg);
            }
          });
        }
      });
    }
  }
};
</script>

<style scoped>
</style>
