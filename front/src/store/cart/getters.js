export default {
  /* getters可以帮我们从state中进一步过滤我们需要的一些状态条件 */

  /* 购物车列表 */
  carts: state => {
    return Object.keys(state.cart);
  },
  /* 判断商品是否已经在购物车内 */
  hasGoods: state => (goods) => {
    let cart = state.cart[`${goods.rid}`];
    if (cart) return `${goods.gid}` in cart.goods; else return false;
  },
  /* 返回商品列表 */
  cartGoods: state => (rid) => {
    let cart = state.cart[rid];
    if (cart) {
      let res = [];
      let goods = cart.goods;
      for (let key in goods) {
        res.push(goods[key]);
      }
      return res;
    } else return [];
  },
  /* 判断套餐是否已经在购物车内 */
  hasCombo: state => (combo) => {
    let cart = state.cart[`${combo.rid}`];
    if (cart) return `${combo.cid}` in cart.combos; else return false;
  },
  /* 返回商品列表 */
  cartCombos: state => (rid) => {
    let cart = state.cart[rid];
    if (cart) {
      let res = [];
      let combos = cart.combos;
      for (let key in combos) {
        res.push(combos[key]);
      }
      return res;
    } else return [];
  },
  /* 计算单个商家的金额 */
  cartMoney: state => (rid) => {
    let money = 0;
    let cart = state.cart[rid];
    if (cart) {
      let goods = cart.goods;
      for (let item in goods) {
        money += goods[item].price * goods[item].num;
      }
      let combos = cart.combos;
      for (let item in combos) {
        money += combos[item].price * combos[item].num;
      }
    }
    return money;
  },
  /* 计算购物车的总金额 */
  totalMoney: state => {
    let money = 0;
    for (let item in state.cart) {
      let cart = state.cart[item];
      let goods = cart.goods;
      for (let item in goods) {
        money += goods[item].price * goods[item].num;
      }
      let combos = cart.combos;
      for (let item in combos) {
        money += combos[item].price * combos[item].num;
      }
    }
    return Number(money);
  }
};
