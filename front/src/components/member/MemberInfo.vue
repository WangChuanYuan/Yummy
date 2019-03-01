<template>
  <el-form :model="memberInfo" ref="memberInfo">
    <h3>个人资料</h3>
    <hr/>
    <el-form-item label="头像" style="padding-top: 3%">
      <AvatarUploader :avatar="avatar" @upload="uploadAvatar"/>
    </el-form-item>
    <el-form-item label="邮箱">
      <span>{{memberInfo.id}}</span>
    </el-form-item>
    <el-form-item label="等级">
      <el-popover
        placement="right"
        title="等级"
        width="150"
        trigger="hover">
        <span>会员等级可享受消费折扣</span>
        <br/>
        <span>0级: 不享受折扣</span>
        <br/>
        <span>1级: 九八折</span>
        <br/>
        <span>2级: 九五折</span>
        <br/>
        <span>3级: 九二折</span>
        <br/>
        <span>4级: 八八折</span>
        <br/>
        <span>5级: 八五折</span>
        <i class="el-icon-info" slot="reference"></i>
      </el-popover>
      <br/>
      <span>{{memberInfo.level}}</span>
    </el-form-item>
    <el-form-item label="经验" style="width: 200px">
      <el-popover
        placement="right"
        title="经验"
        width="150"
        trigger="hover">
        <span>单次消费每10元获得10点经验，不足10元的部分不获经验。</span>
        <br/>
        <span>0-50: 0级</span>
        <br/>
        <span>50-200: 1级</span>
        <br/>
        <span>200-500: 2级</span>
        <br/>
        <span>500-1500: 3级</span>
        <br/>
        <span>1500-3000: 4级</span>
        <br/>
        <span>3000-9999: 5级</span>
        <i class="el-icon-info" slot="reference"></i>
      </el-popover>
      <el-progress :text-inside="false" :stroke-width="15" :percentage="memberInfo.experience / 9999"/>
      <div style="position: relative; top: -27px; left: 60px">{{memberInfo.experience}} / 9999</div>
    </el-form-item>
  </el-form>
</template>

<script>
import Api from '../../assets/js/api';
import AvatarUploader from '../AvatarUploader';

export default {
  name: 'MemberInfo',
  components: {AvatarUploader},
  data () {
    return {
      avatar: sessionStorage.getItem('user') ? JSON.parse(sessionStorage.getItem('user').avatar) : require('@/assets/image/avatar.jpg'),
      avatarRaw: null,
      memberInfo: {
        id: '',
        level: 0,
        experience: 0
      }
    };
  },
  mounted () {
    Api.get('/get_member', {
      'id': sessionStorage.getItem('id')
    }).then((data) => {
      if (data) this.memberInfo = data;
    }).catch(() => {
    });
  },
  methods: {
    uploadAvatar (avatar) {
      this.avatar = avatar.url;
      this.avatarRaw = avatar.raw;
    }
  }
};
</script>

<style scoped>
  .el-form-item {
    padding-left: 5%;
  }
</style>
