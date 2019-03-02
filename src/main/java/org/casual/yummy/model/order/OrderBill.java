package org.casual.yummy.model.order;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.Embeddable;

@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class OrderBill {

    private double deliveryExp;

    private double goodsTotal;

    private double combosTotal;

    private double total;

    // 会员折扣后费用
    private double finalFee;
}
