<template>
  <div class="bg">
    <div class="login">
      <div style="margin: 30px auto 30px auto">
        <span class="logo-prefix">Yummy!</span>
        <span class="logo-suffix">后台</span>
      </div>
      <el-form :model="loginForm" ref="loginForm" :rules="loginRule" class="center" style="width: 45%">
        <el-form-item prop="id" label="账号">
          <el-input v-model="loginForm.id" placeholder="账号"></el-input>
        </el-form-item>
        <el-form-item prop="password" label="密码">
          <el-input v-model="loginForm.password" type="password" placeholder="密码"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="login('loginForm')">登录</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script>
import {Code, Role} from '../assets/js/attrib';
import Api from '../assets/js/api';
import Footer from '../components/Footer';

export default {
  name: 'ManagerLogin',
  components: {Footer},
  data () {
    return {
      role: Role.MANAGER,
      loginForm: {
        id: null,
        password: null
      },
      loginRule: {
        id: [
          {
            required: true,
            message: '请输入账号',
            trigger: 'blur'
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
    login: function (formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          Api.post('/login', {
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
  .bg {
    height: 100%;
    background-image: url('../assets/image/sea.jpg');
  }

  .logo-prefix {
    color: transparent;
    font-size: 40px;
    text-align: center;
    -webkit-text-stroke: 2px var(--theme-blue);
    letter-spacing: 0.04em;
  }

  .logo-suffix {
    color: transparent;
    font-family: "宋体", "Arial Narrow", HELVETICA, serif;
    font-size: 20px;
    text-align: center;
    -webkit-text-stroke: 1px var(--theme-golden);
    letter-spacing: 0.04em;
  }

  .login {
    position: fixed;
    top: 25%;
    left: 30%;
    height: 50%;
    width: 36%;
    /* 透明 */
    background: #fff;
    opacity: 0.60;
    filter: alpha(opacity:60);
  }
</style>
