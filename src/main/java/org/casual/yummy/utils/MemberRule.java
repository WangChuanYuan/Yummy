package org.casual.yummy.utils;

public class MemberRule {

    /**
     * 会员等级0-5
     * 会员等级对应折扣
     */
    public static final double[] LEVEL_FAVOUR = new double[]{1, 0.98, 0.95, 0.92, 0.88, 0.85};

    /**
     * 会员等级0-5
     * 会员等级对应经验下限
     */
    public static final int[] LEVEL_EXPERIENCE = new int[]{0, 50, 200, 500, 1500, 3000};

    /**
     * 下单后支付时间限制
     */
    public static final int ORDER_PAY_MINUTES_LIMIT = 2;

    /**
     * 退订时间与退还金额比例
     */
    public static final int[] UNSUBSCRIBED_MINUTES_RANGE = new int[]{0, 10 ,20, 30, 60};

    public static final double[] UNSUBSCRIBED_MONEY_RATIO_RANGE = new double[]{0.9, 0.85, 0.8, 0.4, 0.2};
}
