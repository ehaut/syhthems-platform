package top.sunriseydy.syhthems.sso.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import top.sunriseydy.syhthems.common.util.ResultUtils;
import top.sunriseydy.syhthems.common.vo.ResultVO;
import top.sunriseydy.syhthems.db.model.User;
import top.sunriseydy.syhthems.db.service.UserService;
import top.sunriseydy.syhthems.db.util.UserUtils;

/**
 * @author SunriseYDY
 * @date 2019-04-12 10:27
 */
@Controller
@RequestMapping("/sso")
public class SsoUserController {

    @Autowired
    UserService userService;

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("isAuthenticated", UserUtils.isLogin());
        model.addAttribute("userDetails", UserUtils.getCustomUserDetails());
        return "login";
    }

    @PostMapping("/register")
    @ResponseBody
    public ResultVO register(User user) {
        userService.registerUser(user);
        return ResultUtils.success();
    }

    @GetMapping("/user/check_user_name")
    @ResponseBody
    public String checkUserName(@RequestParam(required = true) String username) {
        return userService.existsWithUsername(username) ? "false" : "true";
    }

    @GetMapping("/user/check_user_email")
    @ResponseBody
    public String checkUserEmail(@RequestParam(required = true) String email) {
        return userService.existsWithEmail(email) ? "false" : "true";
    }
}
