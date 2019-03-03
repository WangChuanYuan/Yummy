package org.casual.yummy.dto;

import lombok.Data;
import org.casual.yummy.model.order.Order;
import org.casual.yummy.model.order.OrderBill;
import org.casual.yummy.model.order.OrderStatus;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class OrderDTO {

    private Long oid;

    private String mid;

    private String mName;

    private String mAvatar;

    private String rid;

    private String rName;

    private String rAvatar;

    private String detailLocation;

    private String cardNo;

    private List<GoodsDTO> goods;

    private List<ComboDTO> combos;

    private OrderBill bill;

    private LocalDateTime orderTime;

    private LocalDateTime predictedArrivalTime;

    private LocalDateTime arrivalTime;

    private String tip;

    private double rate;

    private OrderStatus status;

    public OrderDTO(Order order) {
        this.oid = order.getOid();
        this.mid = order.getMember().getId();
        this.mName = order.getAddress().getName();
        this.mAvatar = order.getMember().getAvatar();
        this.rid = order.getRestaurant().getId();
        this.rName = order.getRestaurant().getRegisterInfo().getName();
        this.rAvatar = order.getRestaurant().getAvatar();
        this.detailLocation = order.getAddress().getAnchor().getDetailLocation();
        this.cardNo = order.getBankCard().getCardNo();
        this.goods = order.getGoods().entrySet().parallelStream().map(et -> new GoodsDTO(et.getKey(), et.getValue())).collect(Collectors.toList());
        this.combos = order.getCombos().entrySet().parallelStream().map(et -> new ComboDTO(et.getKey(), et.getValue())).collect(Collectors.toList());
        this.bill = order.getBill();
        this.orderTime = order.getOrderTime();
        this.predictedArrivalTime = order.getPredictedArrivalTime();
        this.arrivalTime = order.getArrivalTime();
        this.tip = order.getTip();
        this.rate = order.getRate();
        this.status = order.getStatus();
    }
}
