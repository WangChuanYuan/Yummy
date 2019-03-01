package org.casual.yummy.model.order;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.casual.yummy.model.member.Address;
import org.casual.yummy.model.member.Member;
import org.casual.yummy.model.restaurant.Restaurant;

import javax.persistence.*;
import java.time.LocalDateTime;

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

    private LocalDateTime orderTime;

    private LocalDateTime arrivalTime;

    @Enumerated(value = EnumType.STRING)
    private OrderStatus status;
}
