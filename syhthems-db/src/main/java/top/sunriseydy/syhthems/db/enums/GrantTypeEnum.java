package top.sunriseydy.syhthems.db.enums;

import top.sunriseydy.syhthems.common.enums.BaseEnum;

/**
 * oauth 授权类型枚举
 *
 * @author SunriseYDY
 * @date 2019-04-03 21:40
 */
public enum GrantTypeEnum implements BaseEnum<String, String> {
    /**
     * 授权码模式
     */
    AUTHORIZATION_CODE("authorization_code", "授权码模式"),

    /**
     * 简化模式
     */
    IMPLICIT_GRANT("implicit", "简化模式"),

    /**
     * 密码模式
     */
    RESOURCE_OWNER_PASSWORD_CREDENTIALS_GRANT("password", "密码模式"),

    /**
     * 客户端模式
     */
    CLIENT_CREDENTIALS("client_credentials", "客户端模式"),

    /**
     * 刷新token
     */
    REFRESH_TOKEN("refresh_token", "刷新令牌"),
    ;

    private String key;

    private String value;


    @Override
    public String getKey() {
        return this.key;
    }

    @Override
    public String getValue() {
        return this.value;
    }

    GrantTypeEnum(String key, String value) {
        this.key = key;
        this.value = value;
    }

    @Override
    public String toString() {
        return this.getKey();
    }
}
