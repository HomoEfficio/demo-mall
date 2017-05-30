package skplanet.recopick.demo.mall.domain;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author homo.efficio@gmail.com
 *         created on 2017-05-23
 */
@Entity
@Table(name = "ORDER_ITEM")
//@IdClass(OrderItemId.class) // IdClass를 적용하면 product에 강제로 insert cascading이 발생함
@Getter
@Setter
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,
                  property = "orderItemId")
public class OrderItem extends BaseEntity implements Serializable {

    // @IdClass 방식은 product에 강제 insert cascading이 발생함
    @EmbeddedId
    private OrderItemId orderItemId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_code")
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order orders;

    @Column(name = "quantity")
    private int quantity;

    @Column(name = "amounts")
    private int amounts;
}
