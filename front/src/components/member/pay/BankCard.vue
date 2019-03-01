<template>
  <el-card class="bar-card clear-fix" :body-style="{ padding: '0px' }">
    <div class="clear-fix" @click="select">
      <div class="el-icon-menu" style="font-size: 30px; padding-left: 5px; float: left"></div>
      <div style="font-size: 30px; padding-left: 8px; padding-right: 8px; float: left">|</div>
      <div class="omission">
        <span>卡号: {{inCard.cardNo}}</span>
        <div style="margin-right: 20px; float: right" v-if="aim === 'manage'">
          <el-button type="text" @click="unbind">解绑</el-button>
        </div>
        <br/>
        <span>余额: {{inCard.balance}}</span>
      </div>
    </div>
  </el-card>
</template>

<script>
export default {
  name: 'BankCard',
  props: {
    'aim': {
      // manage || display
      type: String,
      default: 'manage'
    },
    'bankcard': {
      type: Object,
      default: function () {
        return {
          cardNo: '',
          password: '',
          balance: 0
        };
      }
    }
  },
  data () {
    return {
      inCard: JSON.parse(JSON.stringify(this.bankcard))
    };
  },
  watch: {
    bankcard (val) {
      this.inCard = JSON.parse(JSON.stringify(val));
    }
  },
  methods: {
    unbind () {
      this.$emit('unbind');
    },
    select () {
      this.$emit('select', this.inCard.cardNo);
    }
  }
};
</script>

<style scoped>
  .bar-card {
    width: 520px;
    height: 40px;
    padding: 15px 0 15px 0;
    margin-bottom: 10px;
    text-align: left;
  }
</style>
