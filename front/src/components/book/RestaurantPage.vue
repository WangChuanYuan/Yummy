<template>
  <el-container>
    <el-header style="margin-top: 20px">
      <CategorySelector class="center" title="商家分类" :categories="types" :width="1110" label="label" value="value" @select="selectRestaurantType"/>
    </el-header>
    <el-main class="restaurants">
      <el-row>
        <el-col :span="6" v-for="item in restaurants" :key="item.id">
          <RestaurantCard/>
        </el-col>
      </el-row>
    </el-main>
  </el-container>
</template>

<script>
import CategorySelector from '../CategorySelector';
import {RestaurantType} from '../../assets/js/attrib';
import RestaurantCard from '../restaurant/RestaurantCard';

export default {
  name: 'RestaurantPage',
  components: {RestaurantCard, CategorySelector},
  data () {
    return {
      types: RestaurantType,
      restaurants: [{}]
    };
  },
  methods: {
    selectRestaurantType (type) {
    }
  }
};
</script>

<style scoped>
  .restaurants {
    padding: 0;
    margin: 2% 13% 2% 13%;
    min-height: 500px;
    width: 1110px;
    background-color: white;
    box-shadow: 10px 10px 5px var(--theme-medium-grey);
  }
</style>
