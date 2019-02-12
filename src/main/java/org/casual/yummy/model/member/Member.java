package org.casual.yummy.model.member;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.casual.yummy.model.AccountState;
import org.casual.yummy.model.Role;

import javax.persistence.*;
import java.util.List;

@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Member {

    @Id
    private String email;

    private String password;

    private int level;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Address> addresses;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Enumerated(EnumType.STRING)
    private AccountState accountState;
}
