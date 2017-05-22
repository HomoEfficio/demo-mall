package skplanet.recopick.demo.mall.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import skplanet.recopick.demo.mall.domain.Member;
import skplanet.recopick.demo.mall.exception.MemberNotFountException;
import skplanet.recopick.demo.mall.repository.MemberRepository;

import java.util.Optional;

/**
 * @author homo.efficio@gmail.com
 *         created on 2017-05-22
 */
@Controller
public class ViewController {

    @Autowired
    MemberRepository memberRepository;

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @PostMapping("/members/{userName}")
    public String signUp(@PathVariable("userName") String userName, Model model) {
        Optional<Member> optMember = memberRepository.save(new Member(userName));
        model.addAttribute("member", optMember.orElseThrow(() -> new MemberNotFountException()));
        return "main";
    }
}
