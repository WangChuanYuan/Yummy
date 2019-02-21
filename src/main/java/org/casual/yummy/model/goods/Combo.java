package org.casual.yummy.model.goods;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.casual.yummy.model.restaurant.Restaurant;

import javax.persistence.*;
import java.util.List;

@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Combo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cid;

    @Embedded
    private SaleInfo saleInfo;

    @OneToMany(mappedBy = "combo", cascade = CascadeType.ALL)
    private List<ComboItem> items;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant", referencedColumnName = "id")
    private Restaurant restaurant;
}
