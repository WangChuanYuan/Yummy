<template>
  <el-container>
    <el-header>
      <div class="banner"></div>
    </el-header>
    <el-main>
      <el-container>
        <el-header>
          <CategorySelector title="商品分类" :categories="categories" :width="1200" label="name" value="cgid" @select="selectCategory"/>
        </el-header>
        <el-main class="goods">
          <el-row>
            <el-col :span="6" v-for="item in goods" :key="item.gid">
              <GoodsCard aim="purchase"></GoodsCard>
            </el-col>
          </el-row>
        </el-main>
      </el-container>
    </el-main>
  </el-container>
</template>

<script>
import GoodsCard from '../goods/GoodsCard';
import CategorySelector from '../CategorySelector';

export default {
  name: 'GoodsPage',
  components: {CategorySelector, GoodsCard},
  data () {
    return {
      id: this.$route.params.id,
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
    height: 150px;
    background-image: url('../../assets/image/oil.jpg');
  }

  .goods {
    padding: 0;
    margin: 2% 10% 0 10%;
    min-height: 500px;
    width: 1110px;
    background-color: white;
  }
</style>
