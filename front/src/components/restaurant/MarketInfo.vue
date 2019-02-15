<template>
  <div>
    <h3>营销信息</h3>
    <hr/>
    <el-form class="center" style="width: 30%">
      <el-form-item label="头像">
        <AvatarUploader :avatar="marketInfo.avatar" @upload="uploadAvatar"/>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import AvatarUploader from '../AvatarUploader';

export default {
  name: 'MarketInfo',
  components: {AvatarUploader},
  data () {
    return {
      avatarRaw: null,
      marketInfo: {
        avatar: require('../../assets/image/avatar.jpg')
      }
    };
  },
  methods: {
    uploadAvatar (avatar) {
      this.marketInfo.avatar = avatar.url;
      this.avatarRaw = avatar.raw;
    }
  }
};
</script>

<style scoped>
</style>
