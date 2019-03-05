export const Sex = {
  MAN: 'MAN',
  WOMAN: 'WOMAN'
};

export const Code = {
  SUCCESS: 'SUCCESS',
  FAILURE: 'FAILURE',
  INVALID_EMAIL: 'INVALID_EMAIL',
  CANCELED_EMAIL: 'CANCELED_EMAIL',
  WRONG_PASS: 'WRONG_PASS'
};

export const Role = {
  MEMBER: 'MEMBER',
  RESTAURANT: 'RESTAURANT',
  MANAGER: 'MANAGER'
};

export const OrderStatus = {
  ORDERED: {
    value: 'ORDERED',
    label: '已下单'
  },
  PAYED: {
    value: 'PAYED',
    label: '已支付'
  },
  DISPATCHED: {
    value: 'DISPATCHED',
    label: '已派送'
  },
  CANCELED: {
    value: 'CANCELED',
    label: '已取消'
  },
  FINISHED: {
    value: 'FINISHED',
    label: '已完成'
  },
  UNSUBSCRIBED: {
    value: 'UNSUBSCRIBED',
    label: '已退订'
  }
};

export const RegStatus = {
  PENDING: {
    value: 'PENDING',
    label: '未处理'
  },
  ACCESS: {
    value: 'ACCESS',
    label: '通过'
  },
  DENIED: {
    value: 'DENIED',
    label: '拒绝'
  }
};

export const RestaurantType = {
  DELICACY: {
    value: 'DELICACY',
    label: '美食'
  },
  BENTO: {
    value: 'BENTO',
    label: '快餐便当'
  },
  FEATURES: {
    value: 'FEATURES',
    label: '特色菜系'
  },
  EXOTIC: {
    value: 'EXOTIC',
    label: '异国料理'
  },
  SNACK: {
    value: 'SNACK',
    label: '小吃'
  },
  DESSERT: {
    value: 'DESSERT',
    label: '甜品'
  },
  DRINK: {
    value: 'DRINK',
    label: '饮品'
  },
  FRESH: {
    value: 'FRESH',
    label: '果蔬生鲜'
  }, // 果蔬生鲜
  BREAKFAST: {
    value: 'BREAKFAST',
    label: '早餐'
  },
  LUNCH: {
    value: 'LUNCH',
    label: '午餐'
  },
  DINNER: {
    value: 'DINNER',
    label: '晚餐'
  },
  MIDNIGHT: {
    value: 'MIDNIGHT',
    label: '夜宵'
  }
};
