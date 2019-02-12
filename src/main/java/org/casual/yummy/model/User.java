package org.casual.yummy.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
public class User {

    @Id
    private String id;

    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Enumerated(EnumType.STRING)
    private AccountState accountState;
}
