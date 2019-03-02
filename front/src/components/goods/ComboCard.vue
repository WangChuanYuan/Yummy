<template>
  <el-card class="box-card clear-fix" :body-style="{ padding: '0px' }">
    <div class="clear-fix">
      <div class="avatar">
        <img :src="inCombo.avatar">
      </div>
      <div class="info">
        <div class="omission">
          <span style="font-size: 20px; font-weight: bold">{{inCombo.name}}</span>
        </div>
        <el-popover placement="right" trigger="click">
          <GoodsTable :goods="comboGoods"/>
          <el-button slot="reference" type="text">详情</el-button>
        </el-popover>
        <br/>
        <div style="font-size: 13px">
          <span>价格:</span>
          <span>{{inCombo.price}}</span>
        </div>
        <div class="omission" style="font-size: 13px">
          <span>每日供应:</span>
          <span>{{inCombo.dailySupply}}</span>
          <br/>
          <span>库存总量:</span>
          <span>{{inCombo.stock}}</span>
        </div>
      </div>
    </div>
    <div class="combo-op" v-show="aim === 'manage'">
      <el-button type="text" @click="modifyCombo">修改</el-button>
      <el-button type="text" @click="deleteCombo">下架</el-button>
    </div>
    <div class="combo-op" v-show="aim === 'purchase'">
      <el-input-number :min="1" :precision="0" size="mini" v-model="inCombo.num"></el-input-number>
      <el-button type="text" @click="addToCart">加入购物车</el-button>
    </div>
  </el-card>
</template>

<script>
import GoodsTable from './GoodsTable';
import {mapGetters, mapActions} from 'vuex';
import Api from '../../assets/js/api';

export default {
  name: 'ComboCard',
  components: {GoodsTable},
  props: {
    'aim': {
      // manage || purchase
      type: String,
      default: 'manage'
    },
    'combo': {
      type: Object,
      default: function () {
        return {
          cid: 0,
          avatar: require('../../assets/image/cream.jpg'),
          name: '',
          description: '',
          price: 0,
          num: 1,
          dLeft: 0,
          stock: 0,
          startDate: '',
          endDate: ''
        };
      }
    }
  },
  data () {
    return {
      inCombo: JSON.parse(JSON.stringify(this.combo)),
      comboGoods: []
    };
  },
  mounted () {
    this.getComboGoods(this.combo.cid);
  },
  watch: {
    combo (val) {
      this.inCombo = JSON.parse(JSON.stringify(val));
    }
  },
  computed: {
    ...mapGetters({
      hasCombo: 'cart/hasCombo'
    })
  },
  methods: {
    modifyCombo () {
      this.$router.push({
        name: 'editCombo',
        params: {
          cid: this.inCombo.cid,
          aim: 'modify'
        }
      });
    },
    deleteCombo () {
      this.$emit('delete');
    },
    getComboGoods (cid) {
      Api.get('/get_combo_goods', {cid: cid}).then((data) => {
        this.comboGoods = data;
      }).catch(() => {
      });
    },
    ...mapActions({
      'createComboToCart': 'cart/create_combo_to_cart',
      'modifyComboFromCart': 'cart/modify_combo_num_from_cart'
    }),
    addToCart () {
      if (this.inCombo.num > 0) {
        let combo = JSON.parse(JSON.stringify(this.inCombo));
        if (this.hasCombo(combo)) {
          this.modifyComboFromCart({combo: combo, num: combo.num});
        } else this.createComboToCart(combo);
      }
    }
  }
};
</script>

<style scoped>
  .box-card {
    height: 160px;
    width: 275px;
    margin-bottom: 10px;
    padding: 0;
  }

  .avatar {
    padding: 10px 8px 0 5px;
    float: left;
  }

  .avatar img {
    height: 100px;
    width: 100px;
  }

  .info {
    height: 115px;
    width: 160px;
    padding-top: 5px;
    text-align: left;
    float: right;
  }

  .combo-op {
    padding-right: 20px;
    float: right;
  }
</style>
