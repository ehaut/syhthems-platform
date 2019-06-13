package top.sunriseydy.syhthems.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import top.sunriseydy.syhthems.db.model.CustomUserDetails;
import top.sunriseydy.syhthems.db.util.UserUtils;

/**
 * 基础 Controller
 *
 * @author SunriseYDY
 * @date 2019-04-28 11:29
 */
public class BaseController {

    @Autowired
    UserUtils userUtils;

    protected static final String DEFAULT_PAGE = "1";
    protected static final String DEFAULT_PAGE_SIZE = "10";

    protected Jwt getCurrentJwt(@AuthenticationPrincipal Jwt jwt) {
        return jwt;
    }

    protected String getUsername() {
        return UserUtils.getUsername();
    }

    protected CustomUserDetails getCustomUserDetails() {
        return userUtils.getCustomUserDetailsFromSecurityContextHolderWithUsername();
    }
}
