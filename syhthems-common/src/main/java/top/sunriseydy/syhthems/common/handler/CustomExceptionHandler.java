package top.sunriseydy.syhthems.common.handler;

import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.oauth2.provider.error.DefaultWebResponseExceptionTranslator;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import top.sunriseydy.syhthems.common.constants.BaseConstants;
import top.sunriseydy.syhthems.common.enums.ResultEnum;
import top.sunriseydy.syhthems.common.exception.ServiceException;
import top.sunriseydy.syhthems.common.exception.SystemException;
import top.sunriseydy.syhthems.common.util.ResultUtils;
import top.sunriseydy.syhthems.common.vo.ResultVO;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import java.util.stream.Collectors;

/**
 * 系统异常处理.
 *
 * @author SunriseYDY
 * @date 2019-03-14 21:24
 */
@Slf4j
@RestControllerAdvice
@Order(value = BaseConstants.DEFAULT_ORDER)
@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class CustomExceptionHandler {

    @ExceptionHandler({SystemException.class})
    public ResultVO systemExceptionHandler(final SystemException e) {
        log.error("-----> 系统异常：{}", e.getMessage());
        log.error("错误信息:", e);
        return ResultUtils.error(e.getResultEnum().getKey(), e.getMessage());
    }

    /**
     * IllegalArgumentException 异常处理，通常由 Spring 的 {@link org.springframework.util.Assert} 抛出
     *
     * @param e IllegalArgumentException
     * @return ResultEnum.ILLEGAL_ARGUMENT
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResultVO illegalArgumentExceptionHandler(final IllegalArgumentException e) {
        log.error("-----> 参数异常：{}", e.getMessage());
        log.error("错误信息:", e);
        return ResultUtils.error(ResultEnum.ILLEGAL_ARGUMENT.getKey(), e.getMessage());
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResultVO methodArgumentTypeMismatchExceptionHandler(final MethodArgumentTypeMismatchException e) {
        log.error("-----> Controller 参数异常：{}", e.getMessage());
        log.error("错误信息:", e);
        return ResultUtils.error(ResultEnum.ILLEGAL_CONTROLLER_ARGUMENT);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResultVO missingServletRequestParameterExceptionHandler(final MissingServletRequestParameterException e) {
        log.error("-----> Controller 缺少参数异常：{}", e.getMessage());
        log.error("错误信息:", e);
        return ResultUtils.error(ResultEnum.MISSING_SERVLET_REQUEST_PARAMETER);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResultVO httpMessageNotReadableExceptionHandler(final HttpMessageNotReadableException e) {
        log.error("-----> 请求参数读取异常：{}", e.getMessage());
        log.error("错误信息:", e);
        return ResultUtils.error(ResultEnum.HTTP_MESSAGE_NOT_READABLE);
    }

    @ExceptionHandler(ValidationException.class)
    public Object badArgumentExceptionHandler(ValidationException e) {
        log.error("-----> 请求参数错误：{}", e.getMessage());
        log.error("错误信息:", e);
        if (e instanceof ConstraintViolationException) {
            ConstraintViolationException exs = (ConstraintViolationException) e;
            String message = exs.getConstraintViolations()
                    .stream()
                    .map(ConstraintViolation::getMessage)
                    .collect(Collectors.joining(","));
            return ResultUtils.error(ResultEnum.ILLEGAL_CONTROLLER_ARGUMENT.getKey(), message);
        }
        return ResultUtils.error(ResultEnum.ILLEGAL_CONTROLLER_ARGUMENT);
    }

    @ExceptionHandler(BindException.class)
    public Object bindExceptionHandler(BindException e) {
        log.error("-----> 请求参数错误：{}", e.getMessage());
        log.error("错误信息:", e);
        String message = "";
        for (FieldError fieldError : e.getFieldErrors()) {
            message = "对象：" + fieldError.getObjectName() +
                    " 字段：" + fieldError.getField() +
                    " 错误信息：" + fieldError.getDefaultMessage() + "\n";
        }
        return ResultUtils.error(ResultEnum.ILLEGAL_CONTROLLER_ARGUMENT.getKey(), message);
    }

    @ExceptionHandler({ServiceException.class})
    public ResultVO serviceExceptionHandler(final ServiceException e) {
        log.error("-----> 服务异常：{}", e.getMessage());
        log.error("错误信息:", e);
        return ResultUtils.error(e.getResultEnum().getKey(), e.getMessage());
    }

    @ExceptionHandler({BadCredentialsException.class, AuthenticationException.class})
    public ResultVO authenticationExceptionHandler(final Throwable e) {
        log.error("-----> 身份认证异常：{}", e.getMessage());
        log.error("错误信息:", e);
        return ResultUtils.error(ResultEnum.AUTHENCATION_ERROR);
    }

    @ExceptionHandler({AccessDeniedException.class})
    public ResultVO accessExceptionHandler(final Throwable e) {
        log.error("-----> 访问权限异常：{}", e.getMessage());
        log.error("错误信息:", e);
        return ResultUtils.error(ResultEnum.ACCESS_ERROR);
    }

    @ExceptionHandler({UsernameNotFoundException.class})
    public ResultVO accountNotFoundExceptionHandler(final Throwable e) {
        log.error("-----> 账号不存在异常：{}", e.getMessage());
        log.error("错误信息:", e);
        return ResultUtils.error(ResultEnum.ACCOUNT_NOT_FOUND_ERROR);
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResultVO urlNotFoundExceptionHandler(final HttpServletRequest request, final Throwable e) {
        log.error("-----> {} URL不存在异常：{}", request.getRequestURI(), e.getMessage());
        log.error("错误信息:", e);
        return ResultUtils.error(ResultEnum.URL_NOT_FOUND);
    }

    /**
     * OAuth2Exception 异常处理，拦截器中的异常是由 {@link DefaultWebResponseExceptionTranslator} 处理
     * @param e OAuth2Exception
     * @return ResultVO
     */
    @ExceptionHandler(OAuth2Exception.class)
    public ResultVO oAuth2ExceptionHandler(OAuth2Exception e) {
        log.error("-----> OAuth2 认证异常：{}", e.getOAuth2ErrorCode());
        log.error("错误信息:", e);
        return ResultUtils.error(ResultEnum.AUTHENCATION_ERROR.getKey(), e.getOAuth2ErrorCode());
    }

    /**
     * FeignException 异常处理
     * @param e FeignException
     * @return ResultVO
     */
    @ExceptionHandler(FeignException.class)
    public ResultVO<Object> feignExceptionHandler(FeignException e) {
        log.error("-----> feign 请求异常：{}", e.getMessage());
        if (e.hasRequest()) {
            log.error("请求：{}", e.request().toString());
        }
        if (StringUtils.hasText(e.contentUTF8())) {
            log.error("响应：{}", e.contentUTF8());
        }
        return ResultUtils.error(ResultEnum.FEIGN_ERROR);
    }

    @ExceptionHandler({Exception.class})
    public ResultVO globalExceptionHandler(final Exception e) {
        log.error("-----> 全局异常：{} : {}", e.getClass().getName(), e.getMessage());
        log.error("错误信息:", e);
        return ResultUtils.error(ResultEnum.BASE_ERROR.getKey(), e.getClass().getName() + ": " + e.getMessage());
    }
}
