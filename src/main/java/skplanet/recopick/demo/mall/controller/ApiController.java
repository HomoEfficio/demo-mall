package skplanet.recopick.demo.mall.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.AsyncRestTemplate;
import org.springframework.web.context.request.async.DeferredResult;
import skplanet.recopick.demo.mall.domain.Cart;
import skplanet.recopick.demo.mall.domain.CartItem;
import skplanet.recopick.demo.mall.domain.Member;
import skplanet.recopick.demo.mall.domain.Product;
import skplanet.recopick.demo.mall.dto.ProductInfoResultContainerDto;
import skplanet.recopick.demo.mall.dto.SearchResultContainerDto;
import skplanet.recopick.demo.mall.exception.MemberNotFountException;
import skplanet.recopick.demo.mall.repository.CartItemRepository;
import skplanet.recopick.demo.mall.repository.MemberRepository;
import skplanet.recopick.demo.mall.repository.ProductRepository;
import skplanet.recopick.demo.mall.service.CartService;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author homo.efficio@gmail.com
 *         created on 2017-05-22
 */
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ApiController {

    @NonNull private final ObjectMapper objMapper;
    @NonNull private final CartService cartService;
    @NonNull private final ProductRepository productRepository;
    @NonNull private final CartItemRepository cartItemRepository;
    @NonNull private final MemberRepository memberRepository;

    /**
     * 상품 검색
     * 11번가 상품 검색 Open API 사용
     *
     * @param keyword
     * @return
     */
    @GetMapping("/search/{keyword}")
    public DeferredResult<String> search11st(@PathVariable("keyword") String keyword) {

        DeferredResult<String> df = new DeferredResult<>();

        String apiUrl = "http://apis.skplanetx.com/11st/v2/common/products?searchKeyword=" + keyword;
        HttpEntity<String> stringHttpEntity = getStringHttpEntity();

        AsyncRestTemplate asyncRestTemplate = new AsyncRestTemplate();
        ListenableFuture<ResponseEntity<String>> lFuture = asyncRestTemplate.exchange(apiUrl, HttpMethod.GET, stringHttpEntity, String.class);
        lFuture.addCallback(
                result -> {
                    try {
                        SearchResultContainerDto searchResultContainerDto =
                                objMapper.readValue(result.getBody(), SearchResultContainerDto.class);

                        // Product 데이터가 테이블에 없으므로, 검색할 때마다 DB에 넣어 Product 데이터 구성
                        List<Product> products = searchResultContainerDto.getProductSearchResponse().getProducts().getProduct();
                        products.stream()
                                .forEach((p) -> productRepository.save(p));

                        df.setResult(objMapper.writeValueAsString(products));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                },
                error -> {
                    System.out.println(error);
                }
        );

        return df;
    }

    @GetMapping("/products/{productCode}")
    public DeferredResult<String> find(@PathVariable("productCode") String productCode) {

        DeferredResult<String> df = new DeferredResult<>();

        String apiUrl = "http://apis.skplanetx.com/11st/v2/common/products/" + productCode;
        HttpEntity<String> stringHttpEntity = getStringHttpEntity();

        AsyncRestTemplate asyncRestTemplate = new AsyncRestTemplate();
        ListenableFuture<ResponseEntity<String>> lFuture =
                asyncRestTemplate.exchange(apiUrl, HttpMethod.GET, stringHttpEntity, String.class);

        lFuture.addCallback(
                result -> {
                    try {
                        ProductInfoResultContainerDto productInfoResponseDto =
                                objMapper.readValue(result.getBody(), ProductInfoResultContainerDto.class);
                        df.setResult(objMapper.writeValueAsString(
                                productInfoResponseDto.getProductInfoResponse().getProduct()));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                },
                error -> {
                    System.out.println(error);
                }
        );

        return df;
    }

    private HttpEntity<String> getStringHttpEntity() {

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("appKey", "83aeb0b1-94db-3372-9364-22a13e6b6df2");
        httpHeaders.set("Accept", MediaType.APPLICATION_JSON_VALUE);
        httpHeaders.set("Cache-control", "no-cache");
        return new HttpEntity<>(httpHeaders);
    }

    @PostMapping("/carts")
    public void saveCart(@RequestBody Cart cart, BindingResult bindingResult, HttpSession session){
        if (bindingResult.hasErrors()) {
            throw new RuntimeException("Cart Binding Error");
        }
        Long cartId = cartService.save((String)session.getAttribute("userName"), cart);
        System.out.println("saved CardId: " + cartId);
    }

    @GetMapping("/carts")
    public ResponseEntity<Cart> findCart(HttpSession session) {

        Optional<Member> memberOptional = memberRepository.findByUserName((String)session.getAttribute("userName"));
        Member member = memberOptional.orElseThrow(MemberNotFountException::new);

        Optional<Cart> cartOptional = cartService.findCartByMember(member);
        Cart cart = cartOptional.orElseGet(() -> {
            Cart cart1 = new Cart();
            cart1.setMember(member);
            cart1.setCartItems(new ArrayList<CartItem>());
            return cart1;
        });

        return ResponseEntity.ok(cart);
    }
}
