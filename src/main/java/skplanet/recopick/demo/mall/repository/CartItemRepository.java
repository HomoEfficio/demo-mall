package skplanet.recopick.demo.mall.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import skplanet.recopick.demo.mall.domain.Cart;
import skplanet.recopick.demo.mall.domain.CartItem;
import skplanet.recopick.demo.mall.domain.CartItemId;

import java.util.List;

/**
 * @author homo.efficio@gmail.com
 *         created on 2017-05-23
 */
@Repository
public interface CartItemRepository extends CrudRepository<CartItem, CartItemId> {

    List<CartItem> findCartItemsByCart(Cart cart);
}
