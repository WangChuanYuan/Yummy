<template>
  <div>
    <el-form :model="registerForm" :rules="registerRules" ref="registerForm" class="center" style="width: 45%;">
      <el-form-item prop="email" label="用户邮箱">
        <el-input v-model="registerForm.email" type="email" placeholder="邮箱">
          <el-button :disabled="codeButton.isDisabled" slot="append" @click="sendRegisterMail">{{codeButton.name}}
          </el-button>
        </el-input>
      </el-form-item>
      <el-form-item prop="password" label="密码">
        <el-input v-model="registerForm.password" type="password" placeholder="密码"></el-input>
      </el-form-item>
      <el-form-item prop="verifyCode" label="验证码">
        <el-input v-model="registerForm.verifyCode" placeholder="验证码">验证码</el-input>
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
      codeButton: {
        name: '发送验证码',
        isDisabled: false,
        time: 10
      },
      registerForm: {
        email: null,
        password: null,
        verifyCode: null
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
        verifyCode: [
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
      if (/^\w+((.\w+)|(-\w+))@[A-Za-z0-9]+((.|-)[A-Za-z0-9]+).[A-Za-z0-9]+$/.test(this.registerForm.email)) {
        let _this = this;
        _this.codeButton.isDisabled = true;
        let interval = window.setInterval(function () {
          _this.codeButton.name = '(' + _this.codeButton.time + '秒)后重发';
          --_this.codeButton.time;
          if (_this.codeButton.time < 0) {
            _this.codeButton.name = '重新发送验证码';
            _this.codeButton.isDisabled = false;
            _this.codeButton.time = 10;
            window.clearInterval(interval);
          }
        }, 1000);
        Api('/send_register_mail', {
          'email': this.registerForm.email
        }).then((data) => {
          if (data.code === 'SUCCESS') {
            sessionStorage.setItem('verifyCode', data.msg);
          } else {
            this.$message.warning(data.msg);
          }
        });
      } else {
        this.$message.warning('请输入正确的邮箱地址');
      }
    },
    register (formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          if (this.registerForm.verifyCode === sessionStorage.getItem('verifyCode')) {
            Api('/register', {
              'email': this.registerForm.email,
              'password': this.registerForm.password
            }).then((data) => {
              // todo
            });
          } else {
            this.$message.warning('验证码错误');
          }
        }
      });
    }
  }
};
</script>

<style scoped>

</style>
