<template>
  <el-card class="bar-card clear-fix" :body-style="{ padding: '0px' }">
    <div class="clear-fix" @click="select">
      <div class="el-icon-location-outline" style="font-size: 30px; padding-left: 5px; float: left"></div>
      <div style="font-size: 30px; padding-left: 8px; padding-right: 8px; float: left">|</div>
      <div class="omission">
        <span>{{inAddress.name}}</span>
        <span>{{inAddress.sex === 'MAN' ? '先生' : '女士'}}</span>
        <span>{{inAddress.phone}}</span>
      </div>
      <div class="omission" style="color: var(--theme-deep-grey)">
        <span>{{inAddress.location}}</span>
        <span>{{inAddress.detailLocation}}</span>
      </div>
    </div>
  </el-card>
</template>

<script>
import {Sex} from '../../../assets/js/attrib';

export default {
  name: 'AddressBar',
  props: {
    'address': {
      type: Object,
      default: function () {
        return {
          aid: 0,
          name: '',
          sex: Sex.MAN,
          location: '',
          detailLocation: '',
          lng: 0,
          lat: 0,
          phone: ''
        };
      }
    }
  },
  data () {
    return {
      inAddress: JSON.parse(JSON.stringify(this.address))
    };
  },
  watch: {
    address (val) {
      this.inAddress = JSON.parse(JSON.stringify(val));
    }
  },
  methods: {
    select () {
      this.$emit('select', this.inAddress.aid);
    }
  }
};
</script>

<style scoped>
  .bar-card {
    width: 520px;
    height: 40px;
    padding: 15px 0 15px 0;
    margin-bottom: 10px;
    text-align: left;
    cursor: pointer;
  }

  .bar-card:hover {
    border: 1px solid var(--theme-blue);
  }
</style>
