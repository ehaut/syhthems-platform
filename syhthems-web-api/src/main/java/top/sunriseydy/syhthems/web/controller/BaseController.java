package top.sunriseydy.syhthems.web.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;

/**
 * 基础 Controller
 *
 * @author SunriseYDY
 * @date 2019-04-28 11:29
 */
public class BaseController {
    protected static final String DEFAULT_PAGE = "1";
    protected static final String DEFAULT_PAGE_SIZE = "10";

    protected Jwt getCurrentJwt(@AuthenticationPrincipal Jwt jwt) {
        return jwt;
    }
}
