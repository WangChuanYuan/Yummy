<template>
  <div>
    <h3>营销信息</h3>
    <hr/>
    <el-form :model="marketInfo" :rules="infoRules" ref="marketInfo" class="center" style="width: 30%">
      <el-form-item label="头像">
        <AvatarUploader :avatar="avatar" @upload="uploadAvatar"/>
      </el-form-item>
      <el-form-item prop="balance" label="资金">
        <el-input v-model="marketInfo.balance" :readonly="true"></el-input>
      </el-form-item>
      <el-form-item prop="phone" label="联系方式">
        <el-input v-model="marketInfo.phone"></el-input>
      </el-form-item>
      <el-form-item prop="leastExp" label="起送费">
        <el-input v-model="marketInfo.leastExp" type="number"></el-input>
      </el-form-item>
      <el-form-item prop="deliveryExp" label="配送费">
        <el-input v-model="marketInfo.deliveryExp" type="number"></el-input>
      </el-form-item>
      <el-form-item prop="hours" label="营业时间">
        <el-time-picker
          v-model="marketInfo.hours"
          is-range
          value-format="HH:mm:ss"
          range-separator="至"
          start-placeholder="开店时间"
          end-placeholder="闭店时间"
          placeholder="选择营业时间">
        </el-time-picker>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="submit('marketInfo')" style="width: 100%">提交</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import AvatarUploader from '../AvatarUploader';
import Api from '../../assets/js/api';

export default {
  name: 'MarketInfo',
  components: {AvatarUploader},
  data () {
    return {
      avatar: sessionStorage.getItem('user') ? JSON.parse(sessionStorage.getItem('user').avatar) : require('@/assets/image/avatar.jpg'),
      avatarRaw: null,
      marketInfo: {
        balance: 0,
        phone: '',
        leastExp: 0,
        deliveryExp: 0,
        hours: ['08:00:00', '22:00:00']
      },
      infoRules: {
        phone: [
          {
            required: true,
            message: '请输入联系方式',
            trigger: 'blur'
          },
          {
            pattern: /^1[3|4|5|7|8][0-9]\d{8}$/,
            message: '请输入正确的11位手机号',
            trigger: 'blur'
          }
        ],
        leastExp: [
          {
            required: true,
            message: '请输入起送费',
            trigger: 'blur'
          },
          {
            pattern: /^\d+(\.\d+)?$/,
            message: '配送费不低于0',
            trigger: 'blur'
          }
        ],
        deliveryExp: [
          {
            required: true,
            message: '请输入配送费',
            trigger: 'blur'
          },
          {
            pattern: /^\d+(\.\d+)?$/,
            message: '配送费不低于0',
            trigger: 'blur'
          }
        ],
        hours: [
          {
            required: true,
            message: '请选择营业时间',
            trigger: 'blur'
          }
        ]
      }
    };
  },
  mounted () {
    this.init();
  },
  methods: {
    init () {
      Api('/get_restaurant', {
        'id': sessionStorage.getItem('id')
      }).then((data) => {
        if (data) {
          let info = data.marketInfo;
          this.marketInfo.balance = info.balance;
          this.marketInfo.leastExp = info.leastExp;
          this.marketInfo.deliveryExp = info.deliveryExp;
          this.marketInfo.hours = [info.startHour, info.endHour];
        }
      }).catch(() => {
      });
    },
    submit (formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
        }
      });
    },
    uploadAvatar (avatar) {
      this.avatar = avatar.url;
      this.avatarRaw = avatar.raw;
    }
  }
};
</script>

<style scoped>
</style>
