package skplanet.recopick.demo.mall.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author homo.efficio@gmail.com
 *         created on 2017-05-22
 */
@Controller
public class ViewController {

    @RequestMapping("/")
    public String index() {
        return "index";
    }
}
