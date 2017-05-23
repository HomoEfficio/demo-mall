package skplanet.recopick.demo.mall.service;

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
public class CartService {

    private CartRepository cartRepository;
    private MemberRepository memberRepository;
    private CartItemRepository cartItemRepository;

    public CartService(CartRepository cartRepository,
                       MemberRepository memberRepository,
                       CartItemRepository cartItemRepository) {
        this.cartRepository = cartRepository;
        this.memberRepository = memberRepository;
        this.cartItemRepository = cartItemRepository;
    }

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
