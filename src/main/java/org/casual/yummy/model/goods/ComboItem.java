package org.casual.yummy.model.goods;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.*;

@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class ComboItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ciid;

    @ManyToOne
    @JoinColumn(name = "goods", referencedColumnName = "gid")
    private Goods goods;

    private int num;

    @ManyToOne
    @JoinColumn(name = "combo", referencedColumnName = "cid")
    @JsonBackReference
    private Combo combo;
}
