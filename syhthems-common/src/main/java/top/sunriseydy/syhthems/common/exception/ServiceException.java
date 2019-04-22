package top.sunriseydy.syhthems.common.exception;

import top.sunriseydy.syhthems.common.enums.ResultEnum;

/**
 * Service 级别的异常
 *
 * @author SunriseYDY
 * @date 2019-03-14 21:21
 */
public class ServiceException extends RuntimeException {
    private ResultEnum resultEnum = ResultEnum.SERVICE_ERROR;

    public ResultEnum getResultEnum() {
        return resultEnum;
    }

    public void setResultEnum(ResultEnum resultEnum) {
        this.resultEnum = resultEnum;
    }

    public ServiceException() {
        super("服务出现异常");
    }

    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(ResultEnum resultEnum) {
        super(resultEnum.getValue());
        this.resultEnum = resultEnum;
    }

    public ServiceException(String message, ResultEnum resultEnum) {
        super(message);
        this.resultEnum = resultEnum;
    }

    public ServiceException(String message, Throwable cause, ResultEnum resultEnum) {
        super(message, cause);
        this.resultEnum = resultEnum;
    }
}
