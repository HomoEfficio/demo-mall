package skplanet.recopick.demo.mall.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.AsyncRestTemplate;
import org.springframework.web.context.request.async.DeferredResult;
import skplanet.recopick.demo.mall.domain.Cart;
import skplanet.recopick.demo.mall.dto.ProductInfoResultContainerDto;
import skplanet.recopick.demo.mall.dto.SearchResultContainerDto;

import java.io.IOException;

/**
 * @author homo.efficio@gmail.com
 *         created on 2017-05-22
 */
@RestController
@RequestMapping("/api")
public class ApiController {

    private ObjectMapper objMapper;

    @Autowired
    public ApiController(ObjectMapper objMapper) {
        this.objMapper = objMapper;
    }

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
                        df.setResult(objMapper.writeValueAsString(
                                searchResultContainerDto.getProductSearchResponse().getProducts().getProduct()));
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
                         @RequestBody Cart cart) {
        System.out.println(cart);
    }
}
