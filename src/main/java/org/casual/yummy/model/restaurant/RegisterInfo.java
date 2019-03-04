package org.casual.yummy.model.restaurant;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.Embeddable;

@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
/* 餐厅的注册信息修改需要经过经理审核 */
public class RegisterInfo {

    private String name;

    private String email;

    private RestaurantType type;

    private String location;

    private String detailLocation;

    private double lng;

    private double lat;
}
