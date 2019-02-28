<template>
  <div>
    <div>购物车商家号:{{rid}}</div>
    <span>单品</span>
    <el-table :data="goods(rid)">
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
    <el-table :data="combos(rid)">
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
    <hr/>
  </div>
</template>

<script>
import {mapGetters, mapActions} from 'vuex';

export default {
  name: 'CartTable',
  props: {
    'rid': {
      type: String,
      required: true
    }
  },
  computed: {
    ...mapGetters({
      'goods': 'cart/cartGoods',
      'combos': 'cart/cartCombos'
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
    }
  }
};
</script>

<style scoped>

</style>
