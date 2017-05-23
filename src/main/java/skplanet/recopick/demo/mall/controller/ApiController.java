package skplanet.recopick.demo.mall.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.AsyncRestTemplate;
import org.springframework.web.context.request.async.DeferredResult;
import skplanet.recopick.demo.mall.domain.Cart;
import skplanet.recopick.demo.mall.domain.Product;
import skplanet.recopick.demo.mall.dto.ProductInfoResultContainerDto;
import skplanet.recopick.demo.mall.dto.SearchResultContainerDto;
import skplanet.recopick.demo.mall.repository.ProductRepository;
import skplanet.recopick.demo.mall.service.CartService;

import java.io.IOException;
import java.util.List;

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
        AsyncRestTemplate asyncRestTemplate = new AsyncRestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("appKey", "83aeb0b1-94db-3372-9364-22a13e6b6df2");
        httpHeaders.set("Accept", MediaType.APPLICATION_JSON_VALUE);
        httpHeaders.set("Cache-control", "no-cache");
        HttpEntity<String> stringHttpEntity = new HttpEntity<>(httpHeaders);

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
        AsyncRestTemplate asyncRestTemplate = new AsyncRestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("appKey", "83aeb0b1-94db-3372-9364-22a13e6b6df2");
        httpHeaders.set("Accept", MediaType.APPLICATION_JSON_VALUE);
        httpHeaders.set("Cache-control", "no-cache");
        HttpEntity<String> stringHttpEntity = new HttpEntity<>(httpHeaders);

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

    @PostMapping("/carts/{userName}")
    public void saveCart(@PathVariable("userName") String userName,
                         @RequestBody Cart cart){
        Long cartId = cartService.cart(userName, cart);
        System.out.println("saved CardId: " + cartId);
    }
}
