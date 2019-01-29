<template>
  <div>
    <el-form :model="registerForm" :rules="registerRules" ref="registerForm" class="center" style="width: 45%;">
      <el-form-item prop="email" label="用户邮箱">
        <el-input v-model="registerForm.email" type="email" placeholder="邮箱">
          <el-button slot="append" @click="sendRegisterMail">发送验证码</el-button>
        </el-input>
      </el-form-item>
      <el-form-item prop="password" label="密码">
        <el-input v-model="registerForm.password" type="password" placeholder="密码"></el-input>
      </el-form-item>
      <el-form-item prop="code" label="验证码">
        <el-input v-model="registerForm.code" placeholder="验证码">验证码</el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="register('registerForm')">注册</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import Api from '../assets/js/api';

export default {
  name: 'Register',
  data () {
    return {
      registerForm: {
        email: null,
        password: null,
        code: null
      },
      registerRules: {
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
        ],
        code: [
          {
            required: true,
            message: '请输入验证码',
            trigger: 'blur'
          },
          {
            type: 'number',
            message: '验证码为数字',
            trigger: ['blur', 'change']
          }
        ]
      }
    };
  },
  methods: {
    sendRegisterMail () {
      Api('/send_register_mail', {
        'email': this.registerForm.email
      }).then((data) => {
      });
    },
    register (formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          if (this.rememberMe === true) {
            this.$cookies.set('email', this.loginForm.email, 30 * 24 * 60 * 60);
          } else {
            this.$cookies.remove('email');
          }
          Api('/register', {
            'email': this.registerForm.email,
            'password': this.registerForm.password
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
