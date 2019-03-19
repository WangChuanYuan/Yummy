<template>
  <el-container>
    <el-header style="padding: 0; height: 120px">
      <el-row class="banner">
        <el-col :span="12" class="banner-block-wrapper">
          <div class="banner-block">
            <el-popover placement="right" trigger="hover" :content="$route.params.id + ':欢迎您'">
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
            <span>{{restaurant.marketInfo.startHour}}-{{restaurant.marketInfo.endHour}}</span>
          </div>
        </el-col>
      </el-row>
    </el-header>
    <el-main style="padding: 20px 0 0 0">
      <el-container>
        <el-header class="clear-fix">
          <el-radio-group v-model="sort" style="float: left">
            <el-radio-button label="goods">单品</el-radio-button>
            <el-radio-button label="combos">套餐</el-radio-button>
          </el-radio-group>
        </el-header>
        <el-container>
          <el-main v-show="sort === 'goods'" style="padding: 0">
            <el-header>
              <CategorySelector class="center" title="商品分类" :categories="categories" :width="800" label="name"
                                value="cgid" @select="selectCategory"/>
            </el-header>
            <el-main>
              <el-row>
                <el-col :span="6" v-for="item in goods" :key="item.gid">
                  <GoodsCard aim="purchase" :goods="item"></GoodsCard>
                </el-col>
              </el-row>
            </el-main>
          </el-main>
          <el-main v-show="sort === 'combos'">
            <el-row>
              <el-col :span="6" v-for="item in combos" :key="item.cid">
                <ComboCard aim="purchase" :combo="item"></ComboCard>
              </el-col>
            </el-row>
          </el-main>
          <el-aside width="330px">
            <div class="promotion">
              <div class="promotion-banner">
                <span>满减优惠</span>
              </div>
              <div class="promotion-content" v-if="promotions && promotions.length === 0">----- 暂无 -----</div>
              <div v-else>
                <div v-for="item in promotions" :key="item.pid" class="promotion-content">
                  <span>----- 满 {{item.quotaRequired}} 减 <span
                    style="color: red">{{item.quotaOffered}}</span> -----</span>
                  <br/>
                </div>
              </div>
            </div>
          </el-aside>
        </el-container>
      </el-container>
    </el-main>
  </el-container>
</template>

<script>
import GoodsCard from '../goods/GoodsCard';
import ComboCard from '../goods/ComboCard';
import CategorySelector from '../CategorySelector';
import {RestaurantType} from '../../assets/js/attrib';
import Api from '../../assets/js/api';

export default {
  name: 'GoodsPage',
  components: {ComboCard, CategorySelector, GoodsCard},
  data () {
    return {
      id: this.$route.params.id,
      restaurant: {
        id: '',
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
          startHour: '08:00:00',
          endHour: '22:00:00'
        }
      },
      sort: 'goods',
      categories: [],
      cgid: '', // 实际为Number类型，但选择全部时返回的是''
      goods: [],
      combos: [],
      promotions: []
    };
  },
  mounted () {
    Api.get('/get_restaurant', {id: this.$route.params.id}).then((data) => {
      if (data) this.restaurant = data;
    }).catch(() => {
    });
    Api.get('/get_categories', {restaurant: this.$route.params.id}).then((data) => {
      if (data) this.categories = data;
    }).catch(() => {
    });
    Api.get('/get_promotions', {restaurant: this.$route.params.id}).then((data) => {
      if (data) this.promotions = data;
    }).catch(() => {
    });
    this.getGoods();
    this.getCombos();
  },
  methods: {
    selectCategory (cgid) {
      this.cgid = cgid;
      this.getGoods();
    },
    getGoods () {
      let params = {rid: this.$route.params.id};
      if (this.cgid) params['cgid'] = this.cgid;
      Api.get('/get_selling_goods', params).then((data) => {
        if (data) this.goods = data;
      }).catch(() => {
      });
    },
    getCombos () {
      Api.get('/get_selling_combos', {rid: this.$route.params.id}).then((data) => {
        if (data) this.combos = data;
      }).catch(() => {
      });
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

  .promotion {
    margin-right: 150px;
    width: 260px;
    border: 1px solid var(--theme-medium-grey);
  }

  .promotion-banner {
    height: 30px;
    background: var(--theme-blue);
    color: white;
    font-size: 20px;
  }

  .promotion-content {
    font-size: 16px;
    font-weight: bold;
    background: white;
  }
</style>
