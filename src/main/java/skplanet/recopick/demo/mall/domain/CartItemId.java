package skplanet.recopick.demo.mall.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * @author homo.efficio@gmail.com
 *         created on 2017-05-23
 */
@Embeddable
public class CartItemId implements Serializable {

    private Long cart;

    private String product;

    public CartItemId() {
    }

    public CartItemId(Long cart, String product) {
        this.cart = cart;
        this.product = product;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CartItemId that = (CartItemId) o;

        if (!cart.equals(that.cart)) return false;
        return product.equals(that.product);
    }

    @Override
    public int hashCode() {
        int result = cart.hashCode();
        result = 31 * result + product.hashCode();
        return result;
    }
}
