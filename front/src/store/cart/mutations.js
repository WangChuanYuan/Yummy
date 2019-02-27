import * as types from './types.js';
import Vue from 'vue';

export default {
  /* 添加商品到购物车中 */
  [types.CREATE_GOODS_TO_CART] (state, goods) {
    /* 以key-value键值对的形式存放在购物车中 */
    Vue.set(state.goods, `${goods.gid}`, goods);
  },
  /* 删除购物车里的商品 */
  [types.DELETE_GOODS_FROM_CART] (state, goods) {
    /* 删除商品只要删除指定的key */
    Vue.delete(state.goods, `${goods.gid}`);
  },
  /* 修改购物车的商品的数量 */
  [types.MODIFY_GOODS_NUM_FROM_CART] (state, data) {
    let goods = data.goods;
    let num = data.num;
    let item = state.goods[`${goods.gid}`];

    /* 购买次数限制的逻辑这里不写，有需要自己添加 */
    /* 更新 */
    state.goods[`${goods.gid}`] = Object.assign({}, item, {num: num});
  },
  /* 添加套餐到购物车中 */
  [types.CREATE_COMBO_TO_CART] (state, combo) {
    /* 以key-value键值对的形式存放在购物车中 */
    Vue.set(state.combos, `${combo.cid}`, combo);
  },
  /* 删除购物车里的套餐 */
  [types.DELETE_COMBO_FROM_CART] (state, combo) {
    /* 删除套餐只要删除指定的key */
    Vue.delete(state.combos, `${combo.cid}`);
  },
  /* 修改购物车的商品的数量 */
  [types.MODIFY_COMBO_NUM_FROM_CART] (state, data) {
    let combo = data.combo;
    let num = data.num;
    let item = state.combos[`${combo.cid}`];

    /* 购买次数限制的逻辑这里不写，有需要自己添加 */
    /* 更新 */
    state.combos[`${combo.cid}`] = Object.assign({}, item, {num: num});
  },
  /* 更新购物车 */
  [types.UPDATE_CART] (state) {
    sessionStorage.setItem('goods', JSON.stringify(state.goods));
    sessionStorage.setItem('combos', JSON.stringify(state.combos));
  }
};
