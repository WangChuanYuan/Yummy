package org.casual.yummy.model.manager;

import lombok.*;
import lombok.experimental.Accessors;
import org.casual.yummy.model.User;

import javax.persistence.Entity;

@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Entity
public class Manager extends User {

    double balance;
}
