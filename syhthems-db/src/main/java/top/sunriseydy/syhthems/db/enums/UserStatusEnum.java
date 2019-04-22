package top.sunriseydy.syhthems.db.enums;

import top.sunriseydy.syhthems.common.enums.BaseEnum;

/**
 * 用户状态枚举常量
 * 注意，数据库中该字段最长8位
 *
 * @author SunriseYDY
 * @date 2019-03-20 17:34
 */
public enum UserStatusEnum implements BaseEnum<String, String> {
    /**
     * 正常的用户状态
     */
    VALIDE_STATUS("VAL", "用户正常状态"),

    /**
     * 异常的用户状态
     */
    INVALIDE_STATUS("INVAL", "用户状态异常"),

    /**
     * 用户状态为被禁用
     */
    DISABLED_STATUS("DISABLED", "用户被禁用"),
    ;

    /**
     * 用户状态的 key
     */
    private String key;

    /**
     * 用户状态的 value
     */
    private String value;

    UserStatusEnum(String key, String value) {
        this.key = key;
        this.value = value;
    }

    @Override
    public String getKey() {
        return key;
    }

    @Override
    public String getValue() {
        return value;
    }


    @Override
    public String toString() {
        return getKey();
    }
}
