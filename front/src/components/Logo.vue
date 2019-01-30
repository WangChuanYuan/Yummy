<template>
    <div class="logo" v-on:click="toHome">Yummy!</div>
</template>

<script>
export default {
  name: 'Logo',
  methods: {
    toHome () {
      this.$router.push('/');
    }
  }
};
</script>

<style scoped>
  .logo {
    color: transparent;
    font-size: 40px;
    text-align: center;
    -webkit-text-stroke: 2px #409eff;
    letter-spacing: 0.04em;
  }
</style>
