package skplanet.recopick.demo.mall.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author homo.efficio@gmail.com
 *         created on 2017-05-23
 */
@Entity
@Table(name = "CART")
@Getter
@Setter
public class Cart extends BaseEntity implements Serializable {

    @Id
    @GeneratedValue
    @Column(name = "cart_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "cart")
    private List<CartItem> cartItems;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Cart cart = (Cart) o;

        return member.equals(cart.member);
    }

    @Override
    public int hashCode() {
        return member.hashCode();
    }

    // 엔티티 매핑을 하지 않는 방식
//    private String userName;
//
//    @ElementCollection
//    @CollectionTable(name = "CART_ITEM",
//                     joinColumns = @JoinColumn(name = "cart_id"))
//    @OrderColumn(name = "item_idx")
//    private List<CartItem> cartItems;
}
