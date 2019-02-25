<template>
  <el-container>
    <el-header style="padding: 0; height: 120px">
      <el-row class="banner">
        <el-col :span="12" class="banner-block-wrapper">
          <div class="banner-block">
            <el-popover placement="right" trigger="hover">
              <img class="avatar" slot="reference" :src="restaurant.avatar"/>
            </el-popover>
          </div>
        </el-col>
        <el-col :span="4" class="banner-block-wrapper">
          <div class="banner-block">
            <span>起送价</span>
            <br/>
            <span>{{restaurant.marketInfo.leastExp}}</span>
          </div>
        </el-col>
        <el-col :span="4" class="banner-block-wrapper">
          <div class="banner-block">
            <span>配送价</span>
            <br/>
            <span>{{restaurant.marketInfo.deliveryExp}}</span>
          </div>
        </el-col>
        <el-col :span="4" class="banner-block-wrapper">
          <div class="banner-block">
            <span>营业时间</span>
            <br/>
            <span>{{restaurant.marketInfo.starHour}}-{{restaurant.marketInfo.endHour}}</span>
          </div>
        </el-col>
      </el-row>
    </el-header>
    <el-main style="padding: 0">
      <el-container>
        <el-main>
          <el-header style="margin-top: 20px">
            <CategorySelector title="商品分类" :categories="categories" :width="800" label="name" value="cgid" @select="selectCategory"/>
          </el-header>
          <el-main>
            <el-row>
              <el-col :span="8" v-for="item in goods" :key="item.gid">
                <GoodsCard aim="purchase"></GoodsCard>
              </el-col>
            </el-row>
          </el-main>
        </el-main>
        <el-aside></el-aside>
      </el-container>
    </el-main>
  </el-container>
</template>

<script>
import GoodsCard from '../goods/GoodsCard';
import CategorySelector from '../CategorySelector';
import {RestaurantType} from '../../assets/js/attrib';

export default {
  name: 'GoodsPage',
  components: {CategorySelector, GoodsCard},
  data () {
    return {
      id: this.$route.params.id,
      restaurant: {
        id: '0000000',
        avatar: require('../../assets/image/city.jpg'),
        registerInfo: {
          name: '',
          type: RestaurantType.DELICACY,
          location: '',
          detailLocation: '',
          lng: 0,
          lat: 0
        },
        marketInfo: {
          balance: 0,
          phone: '',
          leastExp: 0,
          deliveryExp: 0,
          starHour: '08:00:00',
          endHour: '22:00:00'
        }
      },
      categories: [],
      goods: []
    };
  },
  methods: {
    selectCategory (cgid) {
    }
  }
};
</script>

<style scoped>
  .banner {
    background: url('../../assets/image/cream.jpg') no-repeat center;
    background-size: cover;
  }

  .banner-block-wrapper {
    display: table;
  }

  .banner-block {
    height: 120px;
    font-size: 20px;
    font-weight: bold;
    color: white;
    vertical-align: middle;
    display: table-cell;
  }

  .avatar {
    height: 100px;
    width: 100px;
    border-radius: 100px;
    border: 1px dashed var(--theme-golden);
  }
</style>
