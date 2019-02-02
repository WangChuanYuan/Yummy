<template>
  <div class="footer" style="border-top: 1px solid #c0c0c0">
    <div class="clear-fix">
      <div class="footer-block" style="margin-left: 38%">
        <h4>关于</h4>
      </div>
      <div class="footer-block">
        <h4>博客</h4>
      </div>
      <div class="footer-block">
        <h4>网站</h4>
      </div>
      <div class="footer-block">
        <h4>GitHub</h4>
      </div>
    </div>
    <div>
      <h3 style="margin: 0">Yummy!</h3>
      <small>Copyright © 161250135@smail.nju.edu.cn</small>
    </div>
  </div>
</template>

<script>
export default {
  name: 'Footer'
};
</script>

<style scoped>
  .footer {
    position: fixed;
    bottom: 0;
    width: 100%;
    text-align: center;
    background-color: var(--theme-grey);
  }

  .footer-block {
    float: left;
    margin-left: 5%;
  }
</style>
