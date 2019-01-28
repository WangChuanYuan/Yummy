<template>
  <div>
    <el-form :model="loginForm" :rules="loginRules" ref="loginForm" class="center" style="width: 45%;">
      <el-form-item prop="username" label="用户邮箱">
        <el-input v-model="loginForm.username"></el-input>
      </el-form-item>
      <el-form-item prop="password" label="密码">
        <el-input v-model="loginForm.password" type="password"></el-input>
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
import Ajax from '../assets/js/ajax';

export default {
  name: 'Login',
  data () {
    return {
      loginForm: {
        username: null,
        password: null
      },
      loginRules: {
        username: [
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
  methods: {
    login (formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          Ajax('/login', {
            'username': this.loginForm.username,
            'password': this.loginForm.password
          }).then((data) => {
            if (data === 'SUCCESS') {
              sessionStorage.setItem('user', JSON.stringify(this.loginForm.username));
              window.location.href = './';
            } else if (data === 'INEXISTENCE') {
              this.$message.warning('用户不存在');
            } else if (data === 'WRONG_PASS') {
              this.$message.warning('密码错误');
            } else {
              this.$message.warning('用户名或密码错误');
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
