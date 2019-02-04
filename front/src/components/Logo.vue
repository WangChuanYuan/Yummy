<template>
    <div class="logo" @click="toHome">Yummy!</div>
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
    -webkit-text-stroke: 2px var(--theme-blue);
    letter-spacing: 0.04em;
    cursor: pointer;
  }
</style>
