package top.sunriseydy.syhthems.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.sunriseydy.syhthems.common.util.ResultUtils;
import top.sunriseydy.syhthems.common.vo.ResultVO;
import top.sunriseydy.syhthems.db.model.CustomUserDetails;
import top.sunriseydy.syhthems.db.util.UserUtils;

/**
 * @author SunriseYDY
 * @date 2019-04-15 10:11
 */
@RestController
public class HomeController extends BaseController {
    @Autowired
    UserUtils userUtils;

    @RequestMapping(value = "/")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResultVO home(@AuthenticationPrincipal Jwt jwt) {
        CustomUserDetails userDetails = userUtils.getCustomUserDetailsFromSecurityContextHolderWithUsername();
        return ResultUtils.success(userDetails);
    }
}
