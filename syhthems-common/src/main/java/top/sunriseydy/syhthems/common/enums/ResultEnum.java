package top.sunriseydy.syhthems.common.enums;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * 结果枚举，包含结果码和结果消息
 *
 * @author SunriseYDY
 * @date 2019-03-08 15:03
 */
public enum ResultEnum implements BaseEnum<Integer, String> {

    /**
     * 成功
     */
    SUCCESS(0, "成功"),
    /**
     * 通用错误
     */
    BASE_ERROR(1, "出错啦"),

    /**
     * 数据库错误
     */
    DATABASE_ERROR(2, "数据库出问题啦"),

    /**
     * 身份认证错误
     */
    AUTHENCATION_ERROR(3, "身份认证失败"),

    /**
     * 访问权限错误
     */
    ACCESS_ERROR(4, "此路不通!"),

    /**
     * 账号不存在错误
     */
    ACCOUNT_NOT_FOUND_ERROR(5, "账号不存在"),

    /**
     * URL 不存在错误
     */
    URL_NOT_FOUND(6, "系统中没有该路径!"),

    /**
     * 非法的参数错误
     */
    ILLEGAL_ARGUMENT(7, "参数错误"),

    /**
     * Controller 参数错误
     */
    ILLEGAL_CONTROLLER_ARGUMENT(8, "请求参数错误"),

    /**
     * 请求参数缺失错误
     */
    MISSING_SERVLET_REQUEST_PARAMETER(9, "缺少请求参数"),

    /**
     * 请求参数不可读错误
     */
    HTTP_MESSAGE_NOT_READABLE(10, "请求参数错误"),

    /**
     * 数据库操作失败错误
     */
    DATABASE_OPERATION_ERROR(11, "数据库操作失败"),

    /**
     * SystemException 的默认结果
     */
    SYSTEM_ERROR(12, "系统出现异常"),

    /**
     * ServiceException 的默认结果
     */
    SERVICE_ERROR(13, "服务出现异常"),

    PRODUCT_NOT_FOUND(14, "产品不存在"),

    DEVICE_NOT_FOUND(15, "设备不存在"),

    DATA_STREAM_NOT_FOUND(16, "数据流不存在"),

    FEIGN_ERROR(17, "feign 请求出错"),
    ;

    /**
     * 结果码
     */
    private Integer key;

    /**
     * 结果消息
     */
    private String value;

    ResultEnum(Integer key, String value) {
        this.key = key;
        this.value = value;
    }


    @Override
    @JsonValue
    public Integer getKey() {
        return this.key;
    }

    @Override
    public String getValue() {
        return this.value;
    }

    @Override
    public String toString() {
        return getKey().toString();
    }
}
