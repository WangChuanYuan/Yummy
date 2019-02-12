package org.casual.yummy.model.restaurant;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.casual.yummy.model.User;

import javax.persistence.Entity;

@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Restaurant extends User {

    private String location;

    private String detailLocation;

    private String phone;
}
