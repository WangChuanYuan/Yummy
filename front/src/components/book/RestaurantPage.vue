<template>
  <el-container>
    <el-header style="margin-top: 20px" height="120px">
      <div class="clear-fix">
        <div style="margin-bottom: 10px; float: left">
          <span>{{location ? location : '请选择地址'}}</span>
          <v-region @values="locationChange" style="background-color: white"></v-region>
        </div>
      </div>
      <CategorySelector class="center" title="商家分类" :categories="types" :width="1110" label="label" value="value" @select="selectRestaurantType"/>
    </el-header>
    <el-main class="restaurants">
      <el-row>
        <el-col :span="6" v-for="item in restaurants" :key="item.id">
          <RestaurantCard :restaurant="item" style="margin-bottom: 10px"/>
        </el-col>
      </el-row>
    </el-main>
  </el-container>
</template>

<script>
import CategorySelector from '../CategorySelector';
import {RestaurantType} from '../../assets/js/attrib';
import RestaurantCard from '../restaurant/RestaurantCard';
import Api from '../../assets/js/api';

export default {
  name: 'RestaurantPage',
  components: {RestaurantCard, CategorySelector},
  data () {
    return {
      types: RestaurantType,
      location: '',
      type: '',
      restaurants: []
    };
  },
  mounted () {
    this.getRestaurants();
  },
  methods: {
    locationChange (val) {
      let location = '';
      if (val.province) {
        location += val.province.value;
      }
      if (val.city) {
        location += ',';
        location += val.city.value;
      }
      if (val.area) {
        location += ',';
        location += val.area.value;
      }
      if (val.town) {
        location += ',';
        location += val.town.value;
      }
      this.location = location;
      this.getRestaurants();
    },
    selectRestaurantType (type) {
      this.type = type;
      this.getRestaurants();
    },
    getRestaurants () {
      let params = {};
      if (this.type) params['type'] = this.type;
      if (this.location) params['location'] = this.location;
      Api.get('/get_restaurants', params).then((data) => {
        if (data) this.restaurants = data;
      }).catch(() => {});
    }
  }
};
</script>

<style scoped>
  .restaurants {
    padding: 0;
    margin: 2% 10% 2% 10%;
    min-height: 500px;
    /*width: 100%;*/
    /*background-color: white;*/
    /*box-shadow: 10px 10px 5px var(--theme-medium-grey);*/
  }
</style>
