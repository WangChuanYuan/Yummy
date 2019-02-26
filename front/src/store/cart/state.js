export default {
  /* 购物车 */
  goods: sessionStorage.getItem('goods') ? JSON.parse(sessionStorage.getItem('goods')) : {},
  combos: sessionStorage.getItem('combos') ? JSON.parse(sessionStorage.getItem('combos')) : {}
};
