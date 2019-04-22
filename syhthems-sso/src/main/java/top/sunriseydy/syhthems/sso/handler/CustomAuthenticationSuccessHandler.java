package top.sunriseydy.syhthems.sso.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import tk.mybatis.mapper.version.VersionException;
import top.sunriseydy.syhthems.common.constants.BaseConstants;
import top.sunriseydy.syhthems.common.enums.ResultEnum;
import top.sunriseydy.syhthems.common.exception.ServiceException;
import top.sunriseydy.syhthems.common.util.ResultUtils;
import top.sunriseydy.syhthems.db.model.CustomUserDetails;
import top.sunriseydy.syhthems.db.model.User;
import top.sunriseydy.syhthems.db.service.UserService;
import top.sunriseydy.syhthems.db.util.UserUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 自定义登录成功处理器，返回json数据
 *
 * @author SunriseYDY
 * @date 2019-04-12 19:51
 */
@Slf4j
public class CustomAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    @Autowired
    @Qualifier("commonObjectMapper")
    private ObjectMapper mapper;

    @Autowired
    private UserService userService;

    @Autowired
    private UserUtils userUtils;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        CustomUserDetails userDetails = userUtils.getCustomUserDetailsFromSecurityContextHolderWithUsername();
        if (userDetails == null) {
            throw new ServiceException("未能获取用户对象");
        }
        User user = UserUtils.convertToUser(userDetails);
        log.debug("用户登录成功: {}", user.getUsername());
        try {
            userService.updateLogin(user);
        } catch (VersionException e) {
            response.setContentType(BaseConstants.JSON_UTF8);
            response.getWriter().write(mapper.writeValueAsString(
                    ResultUtils.error(ResultEnum.DATABASE_OPERATION_ERROR)));
        } finally {
            super.clearAuthenticationAttributes(request);
        }
        response.setContentType(BaseConstants.JSON_UTF8);
        response.getWriter().write(mapper.writeValueAsString(
                ResultUtils.success(user)));
        log.debug("更新用户登录状态: {}", user);
    }
}
