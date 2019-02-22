package org.casual.yummy.model.goods;

import com.fasterxml.jackson.annotation.JsonManagedReference;
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
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cgid;

    private String name;

    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant", referencedColumnName = "id")
    private Restaurant restaurant;

    @OneToMany(mappedBy = "category", cascade = CascadeType.REMOVE)
    @JsonManagedReference
    private List<Goods> goods;
}
