<template>
  <div class="side-bar-wrapper" :class="{hidden: isHidden}">
    <div class="side-bar-nav">
      <div class="side-bar-nav-bt" @click="isHidden = false">
        <div class="el-icon-goods"></div>
        <span>购物车</span>
      </div>
    </div>
    <div class="side-bar-ct">
      <div class="side-bar-ct-top" @click="isHidden = true">
        <span>购物车</span>
        <div class="el-icon-d-arrow-right"></div>
      </div>
      <div class="side-bar-ct-main">
        <span>单品</span>
        <el-table :data="goods">
          <el-table-column prop="name" label="名称" width="80"/>
          <el-table-column prop="num" label="数量" width="140">
            <template slot-scope="scope">
              <el-input-number
                :min="0"
                :precision="0"
                size="mini"
                v-model="scope.row.num"
                @change="modifyGoodsNum(scope.row)"/>
            </template>
          </el-table-column>
          <el-table-column prop="price" label="单价" width="60"/>
          <el-table-column type="expand" width="30">
            <template slot-scope="scope">
              <el-form label-position="left" inline>
                <el-form-item label="图像">
                  <img :src="scope.row.avatar" style="height: 40px; width: 40px"/>
                </el-form-item>
                <el-form-item label="类别">
                  <span>{{scope.row.category}}</span>
                </el-form-item>
                <el-form-item label="库存">
                  <span>{{scope.row.stock}}</span>
                </el-form-item>
                <el-form-item label="日供应">
                  <span>{{scope.row.dailySupply}}</span>
                </el-form-item>
                <el-form-item label="起始日期">
                  <span>{{scope.row.startDate}}</span>
                </el-form-item>
                <el-form-item label="结束日期">
                  <span>{{scope.row.endDate}}</span>
                </el-form-item>
                <el-form-item label="描述">
                  <span>{{scope.row.description}}</span>
                </el-form-item>
              </el-form>
            </template>
          </el-table-column>
        </el-table>
        <span>套餐</span>
        <el-table :data="combos">
          <el-table-column prop="name" label="套餐名称" width="80"/>
          <el-table-column prop="num" label="数量" width="140">
            <template slot-scope="scope">
              <el-input-number
                :min="0"
                :precision="0"
                size="mini"
                v-model="scope.row.num"
                @change="modifyComboNum(scope.row)"/>
            </template>
          </el-table-column>
          <el-table-column prop="price" label="套餐价" width="60"/>
          <el-table-column type="expand" width="30">
            <template slot-scope="scope">
              <el-form label-position="left" inline>
                <el-form-item label="图像">
                  <img :src="scope.row.avatar" style="height: 40px; width: 40px"/>
                </el-form-item>
                <el-form-item label="库存">
                  <span>{{scope.row.stock}}</span>
                </el-form-item>
                <el-form-item label="日供应">
                  <span>{{scope.row.dailySupply}}</span>
                </el-form-item>
                <el-form-item label="起始日期">
                  <span>{{scope.row.startDate}}</span>
                </el-form-item>
                <el-form-item label="结束日期">
                  <span>{{scope.row.endDate}}</span>
                </el-form-item>
                <el-form-item label="描述">
                  <span>{{scope.row.description}}</span>
                </el-form-item>
              </el-form>
            </template>
          </el-table-column>
        </el-table>
      </div>
      <div class="side-bar-ct-bottom">
        <span>总价:{{total}}</span>
        <div>
          <el-button type="primary" style="height: 30px; width: 220px" @click="pay">去结算</el-button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import {mapGetters, mapActions} from 'vuex';

export default {
  name: 'ShoppingCart',
  data () {
    return {
      isHidden: true
    };
  },
  computed: {
    ...mapGetters({
      'goods': 'cart/cartGoods',
      'combos': 'cart/cartCombos',
      'total': 'cart/cartMoney'
    })
  },
  methods: {
    ...mapActions({
      'deleteGoodsFromCart': 'cart/delete_goods_from_cart',
      'modifyGoodsNumFromCart': 'cart/modify_goods_num_from_cart',
      'deleteComboFromCart': 'cart/delete_combo_from_cart',
      'modifyComboNumFromCart': 'cart/modify_combo_num_from_cart'
    }),
    modifyGoodsNum (item) {
      let goods = JSON.parse(JSON.stringify(item));
      if (goods.num === 0) {
        this.deleteGoodsFromCart(goods);
      } else {
        this.modifyGoodsNumFromCart({goods: goods, num: goods.num});
      }
    },
    modifyComboNum (item) {
      let combo = JSON.parse(JSON.stringify(item));
      if (combo.num === 0) {
        this.deleteComboFromCart(combo);
      } else {
        this.modifyComboNumFromCart({combo: combo, num: combo.num});
      }
    },
    pay () {
    }
  }
};
</script>

<style scoped>
  .hidden {
    transform: translate(310px, 0);
    transition: all 1s;
  }

  .side-bar-wrapper {
    height: 100%;
    width: 340px;
    position: fixed;
    z-index: 1;
    top: 0;
    right: 0;
    overflow-x: hidden;
  }

  .side-bar-nav {
    height: 100%;
    width: 30px;
    background-color: var(--theme-medium-grey);
  }

  .side-bar-nav-bt {
    width: 30px;
    position: fixed;
    top: 35%;
    color: white;
  }

  .side-bar-nav-bt span {
    writing-mode: vertical-lr;
    text-align: center;
  }

  .side-bar-nav-bt:hover {
    background-color: var(--theme-blue);
  }

  .side-bar-ct {
    height: 100%;
    width: 310px;
    position: fixed;
    top: 0;
    right: 0;
    background-color: var(--theme-grey);
  }

  .side-bar-ct-top {
    height: 30px;
    background-color: white;
  }

  .side-bar-ct-top:hover {
    transform: scale(1.2);
    transition: all 1s;
  }

  .side-bar-ct-main {
    width: 310px;
    position: fixed;
    top: 30px;
    right: 0;
  }

  .side-bar-ct-bottom {
    height: 60px;
    width: 310px;
    position: fixed;
    bottom: 0;
    right: 0;
    color: white;
    background-color: var(--theme-deep-grey);
  }
</style>
