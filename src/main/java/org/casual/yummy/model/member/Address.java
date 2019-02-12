package org.casual.yummy.model.member;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.casual.yummy.model.Sex;

import javax.persistence.*;

@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long aid;

    private String name;

    private Sex sex;

    private String location;

    private String detailLocation;

    private String phone;

    @ManyToOne
    @JoinColumn(name = "member", referencedColumnName = "email")
    private Member member;
}
