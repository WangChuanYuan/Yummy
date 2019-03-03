package org.casual.yummy.model.order;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.casual.yummy.model.goods.Combo;
import org.casual.yummy.model.goods.Goods;
import org.casual.yummy.model.member.Address;
import org.casual.yummy.model.member.BankCard;
import org.casual.yummy.model.member.Member;
import org.casual.yummy.model.restaurant.Restaurant;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Map;

@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long oid;

    @ManyToOne
    @JoinColumn(name = "member", referencedColumnName = "id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "restaurant", referencedColumnName = "id")
    private Restaurant restaurant;

    @ManyToOne
    @JoinColumn(name = "address", referencedColumnName = "aid")
    private Address address;

    @ManyToOne
    @JoinColumn(name = "bankcard", referencedColumnName = "cardNo")
    private BankCard bankCard;

    @ElementCollection
    @MapKeyColumn(name = "goods")
    @Column(name = "num")
    @CollectionTable(name = "Order_Goods", joinColumns = {@JoinColumn(name = "order", referencedColumnName = "oid")})
    private Map<Goods, Integer> goods;

    @ElementCollection
    @MapKeyColumn(name = "combo")
    @Column(name = "num")
    @CollectionTable(name = "Order_Combos", joinColumns = {@JoinColumn(name = "order", referencedColumnName = "oid")})
    private Map<Combo, Integer> combos;

    @Embedded
    private OrderBill bill;

    private LocalDateTime orderTime;

    private LocalDateTime predictedArrivalTime;

    private LocalDateTime arrivalTime;

    private String tip;

    private double rate = 5;

    @Enumerated(value = EnumType.STRING)
    private OrderStatus status;
}
