package skplanet.recopick.demo.mall.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author homo.efficio@gmail.com
 *         created on 2017-05-22
 */
@Entity
@Getter
@Setter
@Table(name = "MEMBER")
public class Member extends BaseEntity implements Serializable {

    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    @NotNull
    @Column(name = "user_name", unique = true)
    private String userName;

    @NotNull
    @Column(name = "birty_year")
    private String birthYear;

    @NotNull
    @Column(name = "gender")
    private String gender;

    @Column(unique = true)
    private String uid;

    public Member() {
    }

    public Member(String userName) {
        this.userName = userName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Member member = (Member) o;

        return userName.equals(member.userName);
    }

    @Override
    public int hashCode() {
        return userName.hashCode();
    }

    //    @OneToOne
//    @JoinColumn(name = "basket_id")
//    private Basket basket;
//
//    @OneToMany(mappedBy = "orderer")
//    private List<Order> orders;
}
