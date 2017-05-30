package skplanet.recopick.demo.mall.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import skplanet.recopick.demo.mall.domain.OrderItem;
import skplanet.recopick.demo.mall.domain.OrderItemId;

/**
 * @author homo.efficio@gmail.com
 *         created on 2017-05-30
 */
@Repository
public interface OrderItemRepository extends CrudRepository<OrderItem, OrderItemId> {
}
