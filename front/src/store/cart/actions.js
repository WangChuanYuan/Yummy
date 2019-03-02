import * as types from './types.js';

export default {
  /* 添加商品到购物车 */
  create_goods_to_cart: ({commit}, goods) => {
    commit(types.CREATE_GOODS_TO_CART, goods);
    commit(types.UPDATE_CART);
  },
  /* 删除购物车的商品 */
  delete_goods_from_cart: ({commit}, goods) => {
    commit(types.DELETE_GOODS_FROM_CART, goods);
    commit(types.UPDATE_CART);
  },
  /* 修改购物车的商品的数量 */
  modify_goods_num_from_cart: ({commit}, data) => {
    commit(types.MODIFY_GOODS_NUM_FROM_CART, data);
    commit(types.UPDATE_CART);
  },
  /* 添加套餐到购物车 */
  create_combo_to_cart: ({commit}, combo) => {
    commit(types.CREATE_COMBO_TO_CART, combo);
    commit(types.UPDATE_CART);
  },
  /* 删除购物车的套餐 */
  delete_combo_from_cart: ({commit}, combo) => {
    commit(types.DELETE_COMBO_FROM_CART, combo);
    commit(types.UPDATE_CART);
  },
  /* 修改购物车的套餐的数量 */
  modify_combo_num_from_cart: ({commit}, data) => {
    commit(types.MODIFY_COMBO_NUM_FROM_CART, data);
    commit(types.UPDATE_CART);
  },
  /* 清空购物车 */
  clear_cart: ({commit}) => {
    commit(types.CLEAR_CART);
    commit(types.UPDATE_CART);
  },
  /* 更新本地购物车 */
  update_cart: ({ commit }) => {
    commit(types.UPDATE_CART);
  }
};
