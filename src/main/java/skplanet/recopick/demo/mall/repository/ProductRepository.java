package skplanet.recopick.demo.mall.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import skplanet.recopick.demo.mall.domain.Product;

/**
 * @author homo.efficio@gmail.com
 *         created on 2017-05-23
 */
@Repository
public interface ProductRepository extends CrudRepository<Product, String> {
}
