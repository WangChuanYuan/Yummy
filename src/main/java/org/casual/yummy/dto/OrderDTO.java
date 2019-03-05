package org.casual.yummy.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.casual.yummy.model.Sex;
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

    private String mname;

    private String mavatar;

    private String rid;

    private String rname;

    private String ravatar;

    private String detailLocation;

    private String cardNo;

    private List<GoodsDTO> goods;

    private List<ComboDTO> combos;

    private OrderBill bill;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime orderTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime predictedArrivalTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime arrivalTime;

    private String tip;

    private double rate;

    private OrderStatus status;

    public OrderDTO(Order order) {
        this.oid = order.getOid();
        this.mid = order.getMember().getId();
        this.mname = order.getAddress().getName() + (order.getAddress().getSex() == Sex.MAN ? "先生" : "女士");
        this.mavatar = order.getMember().getAvatar();
        this.rid = order.getRestaurant().getId();
        this.rname = order.getRestaurant().getRegisterInfo().getName();
        this.ravatar = order.getRestaurant().getAvatar();
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
