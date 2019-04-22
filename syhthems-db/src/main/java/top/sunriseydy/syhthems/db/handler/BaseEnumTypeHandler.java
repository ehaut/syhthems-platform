package top.sunriseydy.syhthems.db.handler;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import top.sunriseydy.syhthems.common.enums.BaseEnum;
import top.sunriseydy.syhthems.common.util.EnumUtils;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * BaseEnum 的实现类的 typeHandler. 实现了枚举类型存储到数据库时采用枚举的 key ，读取时转化为枚举对象
 * <p>
 * 在从数据库读取时对数据进行了强转，如果该枚举类实现了 BaseEnum<K, V>，那么会将数据库读出来的值转化为 K 类型，
 * 然后将其转化为对应的枚举对象: {@link EnumUtils#getEnumByKey(Object, Class)}
 * </p>
 * <p>由于将该处理器注册为默认的Mybatis枚举类型处理器，因此在处理没有实现{@link BaseEnum}接口的枚举类时会出现问题</p>
 *
 * @author SunriseYDY
 * @date 2019-03-20 13:38
 */
public class BaseEnumTypeHandler<T extends Enum<T> & BaseEnum<K, V>, K, V> extends BaseTypeHandler<T> {

    private final Class<T> type;

    public BaseEnumTypeHandler(Class<T> type) {
        if (type == null) {
            throw new IllegalArgumentException("Type argument cannot be null");
        }
        this.type = type;
    }

    public BaseEnumTypeHandler() {
        this.type = null;
    }

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, T parameter, JdbcType jdbcType) throws SQLException {
        if (jdbcType == null) {
            ps.setString(i, parameter.toString());
        } else {
            ps.setObject(i, parameter.getKey(), jdbcType.TYPE_CODE);
        }
    }

    @Override
    public T getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return EnumUtils.getEnumByKey((K) rs.getObject(columnName), type);
    }

    @Override
    public T getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return EnumUtils.getEnumByKey((K) rs.getObject(columnIndex), type);
    }

    @Override
    public T getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return EnumUtils.getEnumByKey((K) cs.getObject(columnIndex), type);
    }
}
