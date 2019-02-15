<template>
  <el-upload class="avatar-uploader" action="mock" :multiple="false" :auto-upload="false"
             :on-change="uploadAvatar" :accept="'image/*'">
    <img v-if="url" :src="url" class="avatar">
    <i v-else class="el-icon-plus avatar-uploader-table"></i>
  </el-upload>
</template>

<script>
export default {
  name: 'AvatarUploader',
  props: {
    'avatar': {
      type: String,
      default: function () {
        return require('../assets/image/avatar.jpg');
      }
    }
  },
  data () {
    return {
      raw: null,
      url: this.avatar
    };
  },
  methods: {
    uploadAvatar (file) {
      this.url = URL.createObjectURL(file.raw);
      this.raw = file.raw;
      this.$emit('upload', {
        'raw': this.raw,
        'url': this.url
      });
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
</style>
