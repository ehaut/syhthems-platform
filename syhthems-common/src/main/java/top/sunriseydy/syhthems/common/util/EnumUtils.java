package top.sunriseydy.syhthems.common.util;

import top.sunriseydy.syhthems.common.enums.BaseEnum;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 自定义字典枚举类常用工具
 *
 * @author SunriseYDY
 * @date 2019-03-08 23:00
 */
public class EnumUtils {
    /**
     * 通过枚举的 <code>key</code> 获取该枚举常量，如果没有对应的枚举常量则返回 null
     *
     * @param key       要查询的 key
     * @param enumClass 枚举类型的class
     * @param <T>       枚举类型
     * @param <K>       Key 的类型
     * @param <V>       Value 的类型
     * @return 该 key 对应的枚举常量，否则返回 null
     */
    public static <T extends BaseEnum<K, V>, K, V> T getEnumByKey(K key, Class<T> enumClass) {
        if (key == null) {
            return null;
        }
        if (key instanceof String && "".equals(key)) {
            return null;
        }
        for (T eachEnum : enumClass.getEnumConstants()) {
            if (key.equals(eachEnum.getKey())) {
                return eachEnum;
            }
        }
        return null;
    }

    /**
     * 通过枚举常量的 key 获取 value
     *
     * @param key       要查询的 key
     * @param enumClass 指定枚举类型class
     * @param <T>       枚举类型
     * @param <K>       key 类型
     * @param <V>       value 类型
     * @return 返回 key 对应的 value，如果未查询到对应的 value 则返回 null
     */
    public static <T extends BaseEnum<K, V>, K, V> V getValueByKey(K key, Class<T> enumClass) {
        T enumObject = getEnumByKey(key, enumClass);
        if (enumObject == null) {
            return null;
        } else {
            return enumObject.getValue();
        }
    }

    /**
     * 将一个枚举类型中所有的常量转化为 Map<K, V>
     *
     * @param enumClass 要转化的枚举 class
     * @param <T>       枚举类型
     * @param <K>       key 类型
     * @param <V>       value 类型
     * @return 返回一个包含枚举常量 key 和 value 的 Map
     */
    public static <T extends BaseEnum<K, V>, K, V> Map<K, V> enum2Map(Class<T> enumClass) {
        return Arrays.stream(enumClass.getEnumConstants())
                .collect(Collectors.toMap(
                        BaseEnum::getKey,
                        BaseEnum::getValue));
    }
}
