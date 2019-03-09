package org.casual.yummy.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.casual.yummy.model.restaurant.RestaurantType;

import java.time.LocalDate;

@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class ConditionDTO {

    public String mid;

    public String rid;

    public RestaurantType restaurantType;

    public Integer memberLevel;

    public LocalDate dateFrom;

    public LocalDate dateTo;

    public Double finalFeeLowerLimit;

    public Double finalFeeUpperLimit;

    public Double actualFeeLowerLimit;

    public Double actualFeeUpperLimit;
}
