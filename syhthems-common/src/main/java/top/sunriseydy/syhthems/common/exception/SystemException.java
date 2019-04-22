package top.sunriseydy.syhthems.common.exception;

import top.sunriseydy.syhthems.common.enums.ResultEnum;

/**
 * 系统级别的异常. 包括Controller、参数校验等异常
 *
 * @author SunriseYDY
 * @date 2019-03-14 21:05
 */
public class SystemException extends RuntimeException {
    private ResultEnum resultEnum = ResultEnum.SYSTEM_ERROR;

    public ResultEnum getResultEnum() {
        return resultEnum;
    }

    public void setResultEnum(ResultEnum resultEnum) {
        this.resultEnum = resultEnum;
    }

    public SystemException() {
        super("系统出现异常");
    }

    public SystemException(String message) {
        super(message);
    }

    public SystemException(ResultEnum resultEnum) {
        super(resultEnum.getValue());
        this.resultEnum = resultEnum;
    }

    public SystemException(String message, ResultEnum resultEnum) {
        super(message);
        this.resultEnum = resultEnum;
    }

    public SystemException(String message, Throwable cause, ResultEnum resultEnum) {
        super(message, cause);
        this.resultEnum = resultEnum;
    }
}
