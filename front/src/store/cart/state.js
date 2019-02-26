export default {
  /* 购物车 */
  cart: sessionStorage.getItem('cart') ? JSON.parse(sessionStorage.getItem('cart')) : {},
};
