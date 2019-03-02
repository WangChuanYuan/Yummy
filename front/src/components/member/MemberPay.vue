<template>
  <div>
    <h3>支付管理</h3>
    <hr/>
    <el-button type="text" @click="editing = true">绑定新卡</el-button>
    <BankCard v-for="(card, index) in bankcards" :key="index" :bankcard="card" @unbind="unbindCard(index)" style="margin-left: 8%" ></BankCard>
    <!-- 绑定新卡 -->
    <el-dialog title="绑定新卡" :visible.sync="editing" width="30%" @open="cardForm.cardNo = ''; cardForm.password = ''">
      <el-form :model="cardForm" ref="cardForm" :rules="cardRule">
        <el-form-item prop="cardNo" label="银行卡号">
          <el-input v-model="cardForm.cardNo" placeholder="银行卡号"></el-input>
        </el-form-item>
        <el-form-item prop="password" label="密码">
          <el-input type="password" v-model="cardForm.password" placeholder="密码"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="bindCard('cardForm')" style="margin-left: 35%">提交</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>
  </div>
</template>

<script>
import Api from '../../assets/js/api';
import {Code} from '../../assets/js/attrib';
import BankCard from './pay/BankCard';

export default {
  name: 'MemberPay',
  components: {BankCard},
  data () {
    return {
      editing: false,
      bankcards: [],
      /* form */
      cardForm: {
        cardNo: '',
        password: ''
      },
      cardRule: {
        cardNo: [
          {
            required: true,
            message: '请输入银行卡号',
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
  mounted () {
    Api.get('/get_bankcards', {
      'mid': sessionStorage.getItem('id')
    }).then((data) => {
      this.bankcards = data;
    }).catch(() => {});
  },
  methods: {
    closeEditor () {
      this.editing = false;
    },
    bindCard (formName) {
      this.$refs[formName].validate(valid => {
        if (valid) {
          Api.post('/bind_bankcard', {
            'bankcard': this.cardForm,
            'mid': sessionStorage.getItem('id')
          }).then((data) => {
            if (data.code === Code.SUCCESS) {
              this.bankcards.push(data.value);
            } else {
              this.$message.warning(data.msg);
            }
            this.closeEditor();
          }).catch(() => {
            this.closeEditor();
          });
        }
      });
    },
    unbindCard (index) {
      this.$confirm('确认解绑该卡？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        Api.post('/unbind_bankcard', {
          'cardNo': this.bankcards[index].cardNo,
          'mid': sessionStorage.getItem('id')
        }).then((data) => {
          if (data.code === Code.SUCCESS) {
            this.bankcards.splice(index, 1);
          } else {
            this.$message.warning(data.msg);
          }
        });
      }).catch(() => {});
    }
  }
};
</script>

<style scoped>

</style>
