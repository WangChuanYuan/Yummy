export default {
  /* 购物车 */
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
  cart: sessionStorage.getItem('cart') ? JSON.parse(sessionStorage.getItem('cart')) : {}
};
