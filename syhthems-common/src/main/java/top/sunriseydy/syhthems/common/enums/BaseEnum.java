package top.sunriseydy.syhthems.common.enums;

import top.sunriseydy.syhthems.common.util.EnumUtils;

/**
 * 枚举基类. 规定标准字段 key 和 value，以及getter方法，用于定义常量，建议有这种需求的都实现该接口。
 * 在json的序列化/反序列化中，配置为使用toString方法作为依据，因此实现该接口的枚举类建议覆盖toString方法，返回key的值
 *
 * @author SunriseYDY
 * @date 2019-03-08 22:00
 * @see EnumUtils
 */
public interface BaseEnum<K, V> {
    /**
     * 获取该枚举对象的key,也就是code
     *
     * @return 枚举对象的key
     */
    K getKey();

    /**
     * 获取该枚举常量的value，也就是meaning
     *
     * @return 枚举对象的value
     */
    V getValue();
}
