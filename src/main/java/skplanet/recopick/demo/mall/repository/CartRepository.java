package skplanet.recopick.demo.mall.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import skplanet.recopick.demo.mall.domain.Cart;
import skplanet.recopick.demo.mall.domain.Member;

import java.util.Optional;

/**
 * @author homo.efficio@gmail.com
 *         created on 2017-05-23
 */
@Repository
public interface CartRepository extends CrudRepository<Cart, Long> {

    Optional<Cart> findByMember(Member member);
}
