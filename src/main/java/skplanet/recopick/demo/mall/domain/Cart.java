package skplanet.recopick.demo.mall.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
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

    private String userName;

    @ElementCollection
    @CollectionTable(name = "CART_ITEM",
                     joinColumns = @JoinColumn(name = "cart_id"))
    @OrderColumn(name = "item_idx")
    private List<CartItem> cartItems;
}
