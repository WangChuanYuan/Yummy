<template>
  <div class="footer" style="border-top: 1px solid var(--theme-medium-grey)">
    <div class="clear-fix">
      <div class="footer-block" style="margin-left: 38%">
        <a>关于</a>
      </div>
      <div class="footer-block">
        <a href="https://wangchuanyuan.github.io">博客</a>
      </div>
      <div class="footer-block">
        <a href="http://software.nju.edu.cn">网站</a>
      </div>
      <div class="footer-block">
        <a href="https://github.com/WangChuanYuan">GitHub</a>
      </div>
    </div>
    <div>
      <h3 style="margin: 0 0 5px 0">Yummy!</h3>
      <b><small>Copyright © 161250135@smail.nju.edu.cn</small></b>
    </div>
  </div>
</template>

<script>
export default {
  name: 'Footer'
};
</script>

<style scoped>
  .footer-block {
    float: left;
    margin-top: 1%;
    margin-bottom: 1%;
    margin-left: 5%;
  }

  a {
    font-size: 20px;
    font-weight: bold;
    text-decoration: none;
  }

  a:link, a:visited, a:hover, a:active{
    color: var(--theme-black);
    text-decoration: none;
  }
</style>
