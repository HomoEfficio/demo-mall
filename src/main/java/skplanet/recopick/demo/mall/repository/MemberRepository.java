package skplanet.recopick.demo.mall.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import skplanet.recopick.demo.mall.domain.Member;

import java.util.Optional;

/**
 * @author homo.efficio@gmail.com
 *         created on 2017-05-22
 */
@Repository
public interface MemberRepository extends PagingAndSortingRepository<Member, Long> {

    Optional<Member> findByUserName(String userName);
    Optional<Member> save(Member member);
}
