<template>
  <el-form :model="registerForm" :rules="registerRules" ref="registerForm" class="center" style="width: 30%">
    <el-form-item prop="id" label="用户邮箱">
      <el-input v-model="registerForm.id" type="email" placeholder="邮箱">
        <el-button :disabled="codeButton.isDisabled" slot="append" @click="sendVerifyMail">{{codeButton.name}}
        </el-button>
      </el-input>
    </el-form-item>
    <el-form-item prop="password" label="密码">
      <el-input v-model="registerForm.password" type="password" placeholder="密码"></el-input>
    </el-form-item>
    <el-form-item prop="verifyCode" label="验证码">
      <el-input v-model="registerForm.verifyCode" placeholder="验证码"></el-input>
    </el-form-item>
    <el-form-item>
      <el-button type="primary" @click="register('registerForm')" style="width: 100%">提交</el-button>
    </el-form-item>
  </el-form>
</template>

<script>
import Api from '../../assets/js/api';
import {Code} from '../../assets/js/attrib';

export default {
  name: 'RegisterForm',
  data () {
    return {
      codeButton: {
        name: '发送验证码',
        isDisabled: false,
        time: 30
      },
      registerForm: {
        id: '',
        password: '',
        verifyCode: ''
      },
      registerRules: {
        id: [
          {
            required: true,
            message: '请输入用户邮箱',
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
        verifyCode: [
          {
            required: true,
            message: '请输入验证码',
            trigger: 'blur'
          }
        ]
      }
    };
  },
  methods: {
    sendVerifyMail () {
      if (/^[A-Za-z\d]+([-_.][A-Za-z\d]+)*@([A-Za-z\d]+[-.])+[A-Za-z\d]{2,4}$/.test(this.registerForm.id)) {
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
        Api.post('/send_verify_mail', {
          'email': this.registerForm.id
        }).then((data) => {
          if (data.code === Code.SUCCESS) {
            this.$message.success(data.msg);
          } else this.$message.warning(data.msg);
        });
      } else {
        this.$message.warning('请输入正确的邮箱地址');
      }
    },
    register (formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          Api.post('/register_member', {
            'id': this.registerForm.id,
            'password': this.registerForm.password,
            'verifyCode': this.registerForm.verifyCode
          }).then((data) => {
            if (data.code === Code.SUCCESS) {
              let user = data.value;
              if (sessionStorage.getItem('id') !== user.id) {
                sessionStorage.clear();
                sessionStorage.setItem('id', user.id);
                sessionStorage.setItem('user', JSON.stringify(user));
              }
              this.$router.push('/memberCenter');
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
