<template>
  <div>
    <el-form :model="loginForm" :rules="loginRules" ref="loginForm" class="center" style="width: 45%;">
      <el-form-item prop="email" label="用户邮箱">
        <el-input v-model="loginForm.email" placeholder="邮箱"></el-input>
      </el-form-item>
      <el-form-item prop="password" label="密码">
        <el-input v-model="loginForm.password" type="password" placeholder="密码"></el-input>
      </el-form-item>
      <el-form-item>
        <el-checkbox v-model="rememberMe">记住我</el-checkbox>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="login('loginForm')">登录</el-button>
        <router-link to="/register">
          <el-button>注册</el-button>
        </router-link>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import Api from '../assets/js/api';

export default {
  name: 'Login',
  data () {
    return {
      rememberMe: false,
      loginForm: {
        email: null,
        password: null
      },
      loginRules: {
        email: [
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
        ],
        password: [
          {
            required: true,
            message: '请输入密码',
            trigger: 'blur'
          }
        ]
      }
    };
  },
  mounted () {
    this.getCookies();
  },
  methods: {
    getCookies () {
      if (this.$cookies.isKey('email')) {
        this.loginForm.email = this.$cookies.get('email');
        this.rememberMe = true;
      }
    },
    login (formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          if (this.rememberMe === true) {
            this.$cookies.set('email', this.loginForm.email, 30 * 24 * 60 * 60);
          } else {
            this.$cookies.remove('email');
          }
          Api('/login', {
            'email': this.loginForm.email,
            'password': this.loginForm.password
          }).then((data) => {
            // todo
          });
        }
      });
    }
  }
};
</script>

<style scoped>
</style>
