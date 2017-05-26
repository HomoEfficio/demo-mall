package skplanet.recopick.demo.mall.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;
import skplanet.recopick.demo.mall.common.Encryptor;
import skplanet.recopick.demo.mall.domain.Member;
import skplanet.recopick.demo.mall.dto.ProductDto;
import skplanet.recopick.demo.mall.dto.ProductInfoResponseDto;
import skplanet.recopick.demo.mall.dto.ProductInfoResultContainerDto;
import skplanet.recopick.demo.mall.exception.MemberNotFountException;
import skplanet.recopick.demo.mall.repository.MemberRepository;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.security.MessageDigest;
import java.util.Objects;
import java.util.Optional;

/**
 * @author homo.efficio@gmail.com
 *         created on 2017-05-22
 */
@Controller
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ViewController {

    @NonNull private final ObjectMapper objMapper;
    @NonNull private final MemberRepository memberRepository;
    @NonNull private final Encryptor encryptor;

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @PostMapping("/members/{userName}")
    @ResponseBody
    public ResponseEntity<Member> signUp(@PathVariable("userName") String userName) {
        Member member = memberRepository.save(new Member(userName));
        Objects.requireNonNull(member, "Member is null");
        return ResponseEntity.ok(member);
    }

    @GetMapping("/main/{userName}")
    public ModelAndView signIn(@PathVariable("userName") String userName, HttpServletRequest request, ModelAndView mv) {
        mv.setViewName("main");
        // TODO: DB에서 조회 후 세션에 memberId, uid 심고 main 화면 반환
        Optional<Member> memberOptional = memberRepository.findByUserName(userName);
        Member member = memberOptional.orElseThrow(MemberNotFountException::new);
        request.getSession().setAttribute("userName", userName);

        mv.addObject("mid", encryptor.sha256hash(userName));

        return mv;  // main 화면에서 memberId로 장바구니 조회해서 있으면 표시하도록
    }

    @GetMapping("/products/{productCode}")
    public ModelAndView find(@PathVariable("productCode") String productCode, ModelAndView mv, HttpServletRequest request) throws IOException {
        mv.setViewName("productDetail");

//        String apiUrl = "http://apis.skplanetx.com/11st/v2/common/products/" + productCode;
//        RestTemplate restTemplate = new RestTemplate();
//        HttpHeaders httpHeaders = new HttpHeaders();
//        httpHeaders.set("appKey", "83aeb0b1-94db-3372-9364-22a13e6b6df2");
//        httpHeaders.set("Accept", MediaType.APPLICATION_JSON_VALUE);
//        httpHeaders.set("Cache-control", "no-cache");
//        HttpEntity<String> stringHttpEntity = new HttpEntity<>(httpHeaders);
//
//        ResponseEntity<String> reProductInfoDto =
//                restTemplate.exchange(apiUrl, HttpMethod.GET, stringHttpEntity, String.class);
//
//        ProductInfoResultContainerDto resContainerDto =
//                objMapper.readValue(reProductInfoDto.getBody(),
//                        ProductInfoResultContainerDto.class);
//        ProductInfoResponseDto productInfoResponseDto = resContainerDto.getProductInfoResponse();
//        ProductDto productDto = productInfoResponseDto.getProduct();
//        mv.addObject("product", productDto);
//        String price = productDto.getProductPrice().getPrice();
//        mv.addObject("productPrice", getIntPriceFrom(price));

        return mv;
    }

    private Integer getIntPriceFrom(String str) {
        return Integer.parseInt(str.substring(0, str.length() - 1)
                                   .replaceAll(",", ""));
    }
}
