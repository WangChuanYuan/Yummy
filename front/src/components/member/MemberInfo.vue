<template>
  <el-form :model="memberInfo" ref="memberInfo">
    <h3>个人资料</h3>
    <hr/>
    <el-form-item label="头像" style="padding-top: 3%">
      <AvatarUploader :avatar="avatar" @upload="uploadAvatar"/>
    </el-form-item>
    <el-form-item label="邮箱">
      {{memberInfo.id}}
    </el-form-item>
    <el-form-item label="等级">
      {{memberInfo.level}}
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
        level: 0
      }
    };
  },
  mounted () {
    Api('/get_member', {
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
