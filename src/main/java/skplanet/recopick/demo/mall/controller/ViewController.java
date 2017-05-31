package skplanet.recopick.demo.mall.controller;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import skplanet.recopick.demo.mall.common.Encryptor;
import skplanet.recopick.demo.mall.domain.Member;
import skplanet.recopick.demo.mall.domain.Order;
import skplanet.recopick.demo.mall.exception.MemberNotFountException;
import skplanet.recopick.demo.mall.repository.MemberRepository;
import skplanet.recopick.demo.mall.service.OrderService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

/**
 * @author homo.efficio@gmail.com
 *         created on 2017-05-22
 */
@Controller
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ViewController {

    @NonNull private final MemberRepository memberRepository;
    @NonNull private final OrderService orderService;
    @NonNull private final Encryptor encryptor;

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @PostMapping("/members")
    @ResponseBody
    public ResponseEntity<Member> signUp(@RequestBody @NotNull Member member) {
        Objects.requireNonNull(member, "Member is null");
        return ResponseEntity.ok(memberRepository.save(member));
    }

    @GetMapping("/main/{userName}")
    public ModelAndView signIn(@PathVariable("userName") String userName, HttpSession session, ModelAndView mv) {
        mv.setViewName("main");
        session.setAttribute("userName", userName);
        setUserInfo(userName, mv);
        return mv;  // main 화면에서 memberId로 장바구니 조회해서 있으면 표시하도록
    }

    private void setUserInfo(String userName, ModelAndView mv) {
        Optional<Member> memberOptional = memberRepository.findByUserName(userName);
        Member member = memberOptional.orElseThrow(MemberNotFountException::new);

        mv.addObject("mid", encryptor.sha256hash(userName));
        mv.addObject("birthYear", member.getBirthYear());
        mv.addObject("gender", member.getGender());
    }

    @GetMapping("/products/{productCode}")
    public ModelAndView find(@PathVariable("productCode") String productCode, HttpSession session, ModelAndView mv) throws IOException {
        mv.setViewName("productDetail");
        // 여기에서는 사용자 정보 세팅 후 화면만 리턴해주고
        // 실제 상품 상세 정보 조회는 productDetail.js에서 ajax로 요청(ftl과 js를 분리하기 위해 이 방식 사용)
        setUserInfo((String)session.getAttribute("userName"), mv);
        return mv;
    }

    @GetMapping("/orders/{orderId}")
    public ModelAndView findOrderById(@PathVariable("orderId") Long orderId, ModelAndView mv) {
        mv.setViewName("orderCompleted");
        Order order = orderService.findOne(orderId);
        mv.addObject("order", order);
        return mv;
    }
}
