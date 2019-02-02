<template>
  <div>
    <Navigation/>
    <Footer/>
  </div>
</template>

<script>
import Navigation from '../components/Navigation';
import Footer from '../components/Footer';

export default {
  name: 'MemberCenter',
  components: {Footer, Navigation}
};
</script>

<style scoped>

</style>
