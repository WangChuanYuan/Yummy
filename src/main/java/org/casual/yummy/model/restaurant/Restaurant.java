package org.casual.yummy.model.restaurant;

import lombok.*;
import lombok.experimental.Accessors;
import org.casual.yummy.model.User;

import javax.persistence.Embedded;
import javax.persistence.Entity;

@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Entity
public class Restaurant extends User {

    @Embedded
    private RegisterInfo registerInfo;

    @Embedded
    private MarketInfo marketInfo;

    private String phone;
}
