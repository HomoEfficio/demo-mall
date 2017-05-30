package skplanet.recopick.demo.mall.domain;

import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * @author homo.efficio@gmail.com
 *         created on 2017-05-23
 */
@Embeddable
public class OrderItemId implements Serializable {

    private Long orders;

    private String product;

    public OrderItemId() {
    }

    public OrderItemId(Long order, String product) {
        this.orders = order;
        this.product = product;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrderItemId that = (OrderItemId) o;

        if (!orders.equals(that.orders)) return false;
        return product.equals(that.product);
    }

    @Override
    public int hashCode() {
        int result = orders.hashCode();
        result = 31 * result + product.hashCode();
        return result;
    }
}
