package skplanet.recopick.demo.mall.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author homo.efficio@gmail.com
 *         created on 2017-05-23
 */
//@Embeddable
@Entity
@Table(name = "CART_ITEM")
//@IdClass(CartItemId.class)
@Getter
@Setter
public class CartItem extends BaseEntity implements Serializable {

//    @Id
//    @GeneratedValue
//    @Column(name = "cart_item_id")
//    private Long id;

    @EmbeddedId
    private CartItemId cartItemId;

//    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_code")
    private Product product;

//    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id")
    private Cart cart;

    @Column(name = "quantity")
    private int quantity;

    @Column(name = "amounts")
    private int amounts;

    // 엔티티 매핑을 하지 않는 방식
//    @Embedded
//    private ProductCode productCode;
//
//    @Column(name = "product_name")
//    private String productName;
//
//    @Column(name = "price")
//    private int productPrice;
//
//    @Column(name = "product_image")
//    private String productImage;
//
//    @Column(name = "point")
//    private String point;
//
//    @Column(name = "chip")
//    private String chip;
//
//    @Column(name = "installment")
//    private String installment;
//
//    @Column(name = "shipFee")
//    private String shipFee;
//
//    @Column(name = "sellSatisfaction")
//    private String sellSatisfaction;
//
//    @Column(name = "sellGrade")
//    private String sellGrade;
//
//    @Column(name = "quantity")
//    private int quantity;
//
//    @Column(name = "amounts")
//    private int amounts;
}
