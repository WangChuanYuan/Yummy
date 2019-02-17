package org.casual.yummy.model.goods;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.Embeddable;
import java.time.LocalDate;

@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class SaleInfo {

    private String avatar;

    private String name;

    private String description;

    private double price;

    private long dailySupply;

    private long stock;

    private LocalDate startDate;

    private LocalDate endDate;
}
