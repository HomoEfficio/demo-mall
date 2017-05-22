package skplanet.recopick.demo.mall.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import skplanet.recopick.demo.mall.domain.Member;
import skplanet.recopick.demo.mall.repository.MemberRepository;

import java.util.Objects;

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
}
