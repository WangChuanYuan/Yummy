<template>
  <el-form :model="memberInfo" ref="memberInfo">
    <h3>个人资料</h3>
    <hr/>
    <el-form-item label="头像" style="padding-top: 3%">
      <AvatarUploader :avatar="memberInfo.avatar" @upload="uploadAvatar"/>
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
      avatarRaw: null,
      memberInfo: {
        avatar: require('@/assets/image/avatar.jpg'),
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
      this.memberInfo.avatar = avatar.url;
      this.avatarRaw = avatar.raw;
    }
  }
};
</script>

<style scoped>
  .avatar-uploader {
    border: 1px dashed #d9d9d9;
    width: 120px;
    height: 120px;
    border-radius: 120px;
    cursor: pointer;
    overflow: hidden;
  }

  .avatar-uploader:hover {
    border-color: var(--theme-blue);
  }

  .avatar-uploader-table {
    font-size: 20px;
    color: #8c939d;
    width: 120px;
    height: 120px;
    line-height: 120px;
    text-align: center;
  }

  .avatar {
    width: 120px;
    height: 120px;
    border-radius: 120px;
    display: block;
  }

  .el-form-item {
    padding-left: 5%;
  }
</style>
