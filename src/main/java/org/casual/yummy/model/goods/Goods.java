package org.casual.yummy.model.goods;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.casual.yummy.model.restaurant.Restaurant;

import javax.persistence.*;

@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Goods {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long gid;

    @Embedded
    private SaleInfo saleInfo;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant", referencedColumnName = "id")
    private Restaurant restaurant;
}
