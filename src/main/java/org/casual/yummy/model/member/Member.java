package org.casual.yummy.model.member;

import lombok.*;
import lombok.experimental.Accessors;
import org.casual.yummy.model.User;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;

@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Entity
public class Member extends User {

    private int level;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Address> addresses;
}
