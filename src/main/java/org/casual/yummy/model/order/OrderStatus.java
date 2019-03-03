package org.casual.yummy.model.order;

public enum OrderStatus {
    ORDERED, // 已下单
    PAYED, // 已支付,
    DISPATCHED, // 已派送
    CANCELED, // 未支付取消
    FINISHED, // 已完成
    UNSUBSCRIBED, // 退订
}
