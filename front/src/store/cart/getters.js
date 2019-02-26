export default {
  /* getters可以帮我们从state中进一步过滤我们需要的一些状态条件 */

  /* 判断商品是否已经在购物车内 */
  hasGoods: state => (goods) => {
    return `${goods.gid}` in state.goods;
  },
  /* 返回商品列表 */
  cartGoods: state => {
    let cart = state.goods;
    let goods = [];
    for (let key in cart) {
      goods.push(cart[key]);
    }
    return goods;
  },
  /* 判断套餐是否已经在购物车内 */
  hasCombo: state => (combo) => {
    return `${combo.cid}` in state.combos;
  },
  /* 返回商品列表 */
  cartCombos: state => {
    let cart = state.combos;
    let combos = [];
    for (let key in cart) {
      combos.push(cart[key]);
    }
    return combos;
  },
  /* 计算购物车的金额 */
  cartMoney: state => {
    let money = 0;
    let goods = state.goods;
    for (let item in goods) {
      money += goods[item].price * goods[item].num;
    }
    let combos = state.combos;
    for (let item in combos) {
      money += combos[item].price * combos[item].num;
    }
    return Number(money);
  }
};
