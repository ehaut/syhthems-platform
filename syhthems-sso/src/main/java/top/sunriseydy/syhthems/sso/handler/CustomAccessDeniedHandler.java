package top.sunriseydy.syhthems.sso.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import top.sunriseydy.syhthems.common.constants.BaseConstants;
import top.sunriseydy.syhthems.common.enums.ResultEnum;
import top.sunriseydy.syhthems.common.util.ResultUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 自定义禁止访问处理器，返回json结果
 *
 * @author SunriseYDY
 * @date 2019-03-18 14:37
 */
public class CustomAccessDeniedHandler implements AccessDeniedHandler {
    @Autowired
    @Qualifier("commonObjectMapper")
    private ObjectMapper mapper;

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        response.setStatus(HttpStatus.FORBIDDEN.value());
        response.setContentType(BaseConstants.JSON_UTF8);
        response.getWriter().write(mapper.writeValueAsString(ResultUtils.error(ResultEnum.ACCESS_ERROR)));
    }
}
