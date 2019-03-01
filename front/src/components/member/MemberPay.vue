<template>
  <div>
    <h3>支付管理</h3>
    <hr/>
    <el-button type="text" @click="editing = true">绑定新卡</el-button>
    <BankCard v-for="(card, index) in bankcards" :key="index" style="margin-left: 8%" @unbind="unbindCard(index)"></BankCard>
    <!-- 绑定新卡 -->
    <el-dialog title="绑定新卡" :visible.sync="editing" width="30%">
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
      bankcards: [{}, {}],
      /* form */
      cardForm: {
        cardNo: '',
        password: ''
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
    bindCard () {
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
