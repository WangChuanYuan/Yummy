package org.casual.yummy.model.goods;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

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

    @ManyToMany
    @JoinTable(name = "combo_goods",
            joinColumns = {@JoinColumn(name = "combo", referencedColumnName = "cid")},
            inverseJoinColumns = {@JoinColumn(name = "goods", referencedColumnName = "gid")})
    private List<Goods> goods;
}
