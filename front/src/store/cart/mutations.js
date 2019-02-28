import * as types from './types.js';
import Vue from 'vue';

/**
 * cart: {
 *          'rid1': {
 *              goods: {
 *                'gid1': Object
 *                ...
 *              },
 *              combos: {
 *                'cid1': Object
 *                ...
 *              }
 *          }
 *          ...
 * }
 */
export default {
  /* 添加商品到购物车中 */
  [types.CREATE_GOODS_TO_CART] (state, goods) {
    if (!state.cart[`${goods.rid}`]) Vue.set(state.cart, `${goods.rid}`, {goods: {}, combos: {}});
    Vue.set(state.cart[`${goods.rid}`]['goods'], `${goods.gid}`, goods);
  },
  /* 删除购物车里的商品 */
  [types.DELETE_GOODS_FROM_CART] (state, goods) {
    Vue.delete(state.cart[`${goods.rid}`]['goods'], `${goods.gid}`);
    /* 删除该购物车 */
    if (Object.keys(state.cart[`${goods.rid}`]['goods']).length === 0 && Object.keys(state.cart[`${goods.rid}`]['combos']).length === 0) {
      Vue.delete(state.cart, `${goods.rid}`);
    }
  },
  /* 修改购物车的商品的数量 */
  [types.MODIFY_GOODS_NUM_FROM_CART] (state, data) {
    let goods = data.goods;
    let num = data.num;
    let item = state.cart[`${goods.rid}`]['goods'][`${goods.gid}`];
    /* 更新 */
    state.cart[`${goods.rid}`]['goods'][`${goods.gid}`] = Object.assign({}, item, {num: num});
  },
  /* 添加套餐到购物车中 */
  [types.CREATE_COMBO_TO_CART] (state, combo) {
    if (!state.cart[`${combo.rid}`]) Vue.set(state.cart, `${combo.rid}`, {goods: {}, combos: {}});
    Vue.set(state.cart[`${combo.rid}`]['combos'], `${combo.cid}`, combo);
  },
  /* 删除购物车里的套餐 */
  [types.DELETE_COMBO_FROM_CART] (state, combo) {
    Vue.delete(state.cart[`${combo.rid}`]['combos'], `${combo.cid}`);
    /* 删除该购物车 */
    if (Object.keys(state.cart[`${combo.rid}`]['goods']).length === 0 && Object.keys(state.cart[`${combo.rid}`]['combos']).length === 0) {
      Vue.delete(state.cart, `${combo.rid}`);
    }
  },
  /* 修改购物车的商品的数量 */
  [types.MODIFY_COMBO_NUM_FROM_CART] (state, data) {
    let combo = data.combo;
    let num = data.num;
    let item = state.cart[`${combo.rid}`]['combos'][`${combo.cid}`];
    /* 更新 */
    state.cart[`${combo.rid}`]['combos'][`${combo.cid}`] = Object.assign({}, item, {num: num});
  },
  /* 更新购物车 */
  [types.UPDATE_CART] (state) {
    sessionStorage.setItem('cart', JSON.stringify(state.cart));
  }
};
