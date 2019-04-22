package top.sunriseydy.syhthems.sso.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import top.sunriseydy.syhthems.db.util.UserUtils;

/**
 * @author SunriseYDY
 * @date 2019-04-12 10:27
 */
@Controller
public class UserController {

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("isAuthenticated", UserUtils.isLogin());
        model.addAttribute("userDetails", UserUtils.getCustomUserDetails());
        return "login";
    }
}
