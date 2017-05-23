package skplanet.recopick.demo.mall.service;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import skplanet.recopick.demo.mall.domain.Cart;
import skplanet.recopick.demo.mall.domain.CartItem;
import skplanet.recopick.demo.mall.domain.CartItemId;
import skplanet.recopick.demo.mall.domain.Member;
import skplanet.recopick.demo.mall.exception.MemberNotFountException;
import skplanet.recopick.demo.mall.repository.CartItemRepository;
import skplanet.recopick.demo.mall.repository.CartRepository;
import skplanet.recopick.demo.mall.repository.MemberRepository;

import java.util.List;
import java.util.Optional;

/**
 * @author homo.efficio@gmail.com
 *         created on 2017-05-23
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CartService {

    @NonNull private final CartRepository cartRepository;
    @NonNull private final MemberRepository memberRepository;
    @NonNull private final CartItemRepository cartItemRepository;

    public Long cart(String userName, Cart cart) {

        Optional<Member> memberOptional = memberRepository.findByUserName(userName);
        Member member = memberOptional.orElseThrow(MemberNotFountException::new);
        cart.setMember(member);

        Optional<Cart> cartOptional = cartRepository.findByMember(member);
        final Cart persistedCart = cartOptional.orElseGet(() -> cartRepository.save(cart));

        List<CartItem> cartItems = cart.getCartItems();
        cartItems.forEach((i) -> {
            i.setCart(persistedCart);
            i.setCartItemId(new CartItemId(persistedCart.getId(), i.getProduct().getProductCode()));
            cartItemRepository.save(i);
        });

        persistedCart.setCartItems(cart.getCartItems());

        return persistedCart.getId();
    }
}
