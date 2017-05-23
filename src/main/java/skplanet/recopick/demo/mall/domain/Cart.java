package skplanet.recopick.demo.mall.domain;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
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
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,
                  property = "id")
public class Cart extends BaseEntity implements Serializable {

    @Id
    @GeneratedValue
    @Column(name = "cart_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;


    // FetchType.LAZY로 하면 단순히 cart를 조회해서 getCartItems() 안 하고
    // 바로 반환하면 cartItems에 cartItem이 들어는 있으나
    // 모든 필드의 값이 null이 됨
    // 따라서 FetchType.EAGER 적용
    @OneToMany(mappedBy = "cart", fetch = FetchType.EAGER)
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
