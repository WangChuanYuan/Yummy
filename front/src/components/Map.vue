<template>
  <div>
    <baidu-map :center="center ? center : '北京'" :scroll-wheel-zoom="true" :double-click-zoom="false" @dblclick="mark">
      <bm-view class="map"></bm-view>
      <bm-local-search :keyword="keyword" :auto-viewport="true" @infohtmlset="getLocation"></bm-local-search>
      <bm-city-list anchor="BMAP_ANCHOR_BOTTOM_LEFT"></bm-city-list>
      <bm-geolocation anchor="BMAP_ANCHOR_BOTTOM_RIGHT" :showAddressBar="true" :autoLocation="false"></bm-geolocation>
    </baidu-map>
    <el-input v-model="keyword" placeholder="输入关键词检索地点"></el-input>
  </div>
</template>

<script>
export default {
  name: 'Map',
  props: {
    'center': {
      default: '北京'
    }
  },
  data () {
    return {
      location: '',
      keyword: ''
    };
  },
  methods: {
    getLocation (poi) {
      this.$emit('locate', poi);
    },
    mark (event) {
      let map = event.target;
      map.clearOverlays();
      let point = event.point;
      /* global BMap */
      let marker = new BMap.Marker(point);
      map.addOverlay(marker);
      (function () {
        marker.addEventListener('rightclick', function () {
          map.removeOverlay(marker);
        });
      })();
      this.$emit('mark', point);
    }
  }
};
</script>

<style scoped>
  .map {
    height: 300px;
    width: 100%;
  }
</style>
