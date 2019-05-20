package top.sunriseydy.syhthems.sso.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.util.StringUtils;
import top.sunriseydy.syhthems.common.constants.BaseConstants;
import top.sunriseydy.syhthems.common.util.ResultUtils;
import top.sunriseydy.syhthems.db.service.UserService;
import top.sunriseydy.syhthems.db.util.UserUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 自定义的注销成功处理器
 *
 * @author SunriseYDY
 * @date 2019-04-12 19:18
 */
public class CustomLogoutSuccessHandler implements LogoutSuccessHandler {
    @Autowired
    @Qualifier("commonObjectMapper")
    private ObjectMapper mapper;

    @Autowired
    private UserService userService;

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        String username = UserUtils.getUsername();
        if (username == null) {
            username = request.getParameter("username");
        }
        String redirectUrl = request.getParameter("redirect_uri");
        // 清除Redis缓存需要提供username参数
        userService.userLogout(username);
        if (StringUtils.hasText(redirectUrl)) {
            response.sendRedirect(redirectUrl);
        } else {
            response.setContentType(BaseConstants.JSON_UTF8);
            response.getWriter().write(mapper.writeValueAsString(
                    ResultUtils.success("登出成功")));
        }
    }
}
