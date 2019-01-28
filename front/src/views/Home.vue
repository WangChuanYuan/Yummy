<template>
  <div>
    <!-- 登录注册 -->
    <div id="login">
      <div id="logo">Yummy!</div>
      <Login style="position: relative; top: 8%"/>
    </div>
    <!-- 背景 -->
    <div class="background">
      <div id="back-top-left"></div>
      <div id="back-top-right"></div>
      <div id="back-bottom-left"></div>
      <div id="back-bottom-right"></div>
    </div>
  </div>
</template>

<script>
import Login from '../components/Login';

export default {
  name: 'Home',
  components: {Login}
};
</script>

<style scoped>
  .background {
    position: fixed;
    height: 100%;
    width: 100%;
    z-index: -1;
  }

  #back-top-left {
    position: fixed;
    top: 0;
    left: 0;
    height: 60%;
    width: 60%;
    background: url("../assets/image/city.jpg") no-repeat #000 top left;
  }

  #back-top-right {
    position: fixed;
    top: 0;
    right: 0;
    height: 60%;
    width: 40%;
    background: url("../assets/image/coffee.jpg") no-repeat #000 top right;
  }

  #back-bottom-left {
    position: fixed;
    top: 60%;
    left: 0;
    height: 40%;
    width: 40%;
    background: url("../assets/image/oil.jpg") no-repeat #000 top right;
  }

  #back-bottom-right {
    position: fixed;
    top: 60%;
    right: 0;
    height: 40%;
    width: 60%;
    background: url("../assets/image/cream.jpg") no-repeat #000 center;
  }

  #login {
    position: fixed;
    top: 25%;
    left: 32%;
    height: 50%;
    width: 36%;
    /* 透明 */
    background: #fff;
    opacity: 0.85;
    filter: alpha(opacity:85);
    /* 边框 */
    box-shadow: 10px 10px 5px #888;
  }

  #logo {
    margin-top: 5%;
    color: transparent;
    font-size: 40px;
    text-align: center;
    -webkit-text-stroke: 1px deepskyblue;
    letter-spacing: 0.04em;
  }
</style>
