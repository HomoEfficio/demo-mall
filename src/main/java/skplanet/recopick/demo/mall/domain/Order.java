package skplanet.recopick.demo.mall.domain;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author homo.efficio@gmail.com
 *         created on 2017-05-24
 */
@Entity
@Table(name = "ORDERS")
@Getter
@Setter
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,
                  property = "id")
public class Order extends BaseEntity implements Serializable {

    @Id
    @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    // FetchType.LAZY로 하면 단순히 order를 조회해서 getOrderItems() 안 하고
    // 바로 반환하면 member에 member가 들어는 있으나
    // 모든 필드의 값이 null이 됨
    // 따라서 FetchType.EAGER 적용
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "member_id")
    @NotNull @NonNull
    private Member member;

    // FetchType.LAZY로 하면 단순히 order를 조회해서 getOrderItems() 안 하고
    // 바로 반환하면 orderItems에 orderItem이 들어는 있으나
    // 모든 필드의 값이 null이 됨
    // 따라서 FetchType.EAGER 적용
    @OneToMany(mappedBy = "orders", fetch = FetchType.EAGER)
    @OrderBy("created_at asc")
    private List<OrderItem> orderItems;

    public Order() {
    }

    public Order(Member member, Cart cart) {
        this.member = member;
        this.orderItems = cartItemsToOrderItems(cart);
    }

    public List<OrderItem> cartItemsToOrderItems(Cart cart) {

        List<OrderItem> orderItems = new ArrayList<>();
        List<CartItem> cartItems = cart.getCartItems();
        for (CartItem cartItem : cartItems) {
            OrderItem orderItem = new OrderItem();
            orderItem.setProduct(cartItem.getProduct());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setAmounts(cartItem.getAmounts());
            orderItems.add(orderItem);
        }

        return orderItems;
    }
}
