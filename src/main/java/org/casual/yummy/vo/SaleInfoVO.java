package org.casual.yummy.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.time.LocalDate;

@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class SaleInfoVO {

    private String avatar;

    private String name;

    private String description;

    private double price;

    /**
     * 今日剩余
     */
    private long dLeft;

    private long stock;

    /**
     * index 0 startDate
     * index 1 endDate
     */
    private LocalDate[] dates;
}
