package skplanet.recopick.demo.mall.service;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import skplanet.recopick.demo.mall.domain.Cart;
import skplanet.recopick.demo.mall.domain.CartItem;
import skplanet.recopick.demo.mall.domain.CartItemId;
import skplanet.recopick.demo.mall.domain.Member;
import skplanet.recopick.demo.mall.exception.CartNotFoundException;
import skplanet.recopick.demo.mall.exception.MemberNotFoundException;
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
@Transactional
public class CartService {

    @NonNull private final CartRepository cartRepository;
    @NonNull private final MemberRepository memberRepository;
    @NonNull private final CartItemRepository cartItemRepository;

    public Long save(String userName, Cart cart) {

        Optional<Member> memberOptional = memberRepository.findByUserName(userName);
        Member member = memberOptional.orElseThrow(MemberNotFoundException::new);
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

    @Transactional(readOnly = true)
    public Optional<Cart> findCartByMember(Member member) {
        return cartRepository.findByMember(member);
    }

    public void emptyCartItemsOf(Member member) {

        Optional<Cart> cartOptional = cartRepository.findByMember(member);
        Cart cart = cartOptional.orElseThrow(CartNotFoundException::new);

        cart.getCartItems()
                .forEach((i) -> cartItemRepository.delete(i.getCartItemId()));
    }
}
