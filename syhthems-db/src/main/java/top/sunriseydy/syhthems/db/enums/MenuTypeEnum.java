package top.sunriseydy.syhthems.db.enums;

import top.sunriseydy.syhthems.common.enums.BaseEnum;

/**
 * 菜单类型字典枚举
 *
 * @author SunriseYDY
 * @date 2019-03-22 09:49
 */
public enum MenuTypeEnum implements BaseEnum<String, String> {
    /**
     * 菜单类型
     */
    MENU("0", "菜单"),
    /**
     * 按钮类型
     */
    BUTTON("1", "按钮"),
    ;

    /**
     * 菜单类型的key，注意在数据库中只有一位
     */
    private String key;

    /**
     * 菜单类型的value
     */
    private String value;

    MenuTypeEnum(String key, String value) {
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
