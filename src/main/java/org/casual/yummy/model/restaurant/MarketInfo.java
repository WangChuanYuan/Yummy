package org.casual.yummy.model.restaurant;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.Embeddable;
import java.time.LocalTime;

@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class MarketInfo {

    private double balance;

    private String phone;

    private double leastExp;

    private double deliveryExp;

    private LocalTime startHour;

    private LocalTime endHour;
}
