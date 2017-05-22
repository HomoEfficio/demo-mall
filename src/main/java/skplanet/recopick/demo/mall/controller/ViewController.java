package skplanet.recopick.demo.mall.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import skplanet.recopick.demo.mall.domain.Member;
import skplanet.recopick.demo.mall.exception.MemberNotFountException;
import skplanet.recopick.demo.mall.repository.MemberRepository;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;
import java.util.Optional;

/**
 * @author homo.efficio@gmail.com
 *         created on 2017-05-22
 */
@Controller
public class ViewController {

    private MemberRepository memberRepository;
    private ObjectMapper objectMapper;

    @Autowired
    public ViewController(MemberRepository memberRepository,
                          ObjectMapper objectMapper) {
        this.memberRepository = memberRepository;
        this.objectMapper = objectMapper;
    }

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
        mv.addObject("member", member);

        return mv;  // main 화면에서 memberId로 장바구니 조회해서 있으면 표시하도록
    }
}
