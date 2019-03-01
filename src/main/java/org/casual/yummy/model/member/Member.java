package org.casual.yummy.model.member;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import lombok.experimental.Accessors;
import org.casual.yummy.model.User;

import javax.persistence.*;
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

    private int experience;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Address> addresses;

    @ManyToMany
    @JoinTable(name = "Member_BankCards",
            joinColumns = {@JoinColumn(name = "member", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "bankcard", referencedColumnName = "cardNo")}
    )
    private List<BankCard> bankCards;
}
