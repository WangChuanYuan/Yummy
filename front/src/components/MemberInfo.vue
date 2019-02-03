<template>
  <el-form :model="memberInfo" ref="memberInfo">
    <h3>个人资料</h3>
    <hr/>
    <el-form-item label="头像" style="padding-top: 3%">
      <el-upload class="avatar-uploader" action="mock" :multiple="false" :auto-upload="false"
                 :on-change="uploadAvatar" :accept="'image/*'">
        <img v-if="memberInfo.avatar" :src="memberInfo.avatar" class="avatar">
        <i v-else class="el-icon-plus avatar-uploader-table"></i>
      </el-upload>
    </el-form-item>
    <el-form-item label="邮箱">
      {{memberInfo.email}}
    </el-form-item>
    <el-form-item label="等级">
      {{memberInfo.level}}
    </el-form-item>
  </el-form>
</template>

<script>
export default {
  name: 'MemberInfo',
  data () {
    return {
      memberInfo: {
        avatar: require('@/assets/image/avatar.jpg'),
        avatarRaw: null,
        email: '161250135@smail.nju.edu.cn',
        level: 0
      }
    };
  },
  methods: {
    uploadAvatar (file) {
      this.memberInfo.avatar = URL.createObjectURL(file.raw);
      this.memberInfo.avatarRaw = file.raw;
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
