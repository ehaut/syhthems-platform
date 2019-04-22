package top.sunriseydy.syhthems.db.service;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
import tk.mybatis.mapper.version.VersionException;

import java.util.List;

/**
 * 通用Service接口
 *
 * @author SunriseYDY
 * @date 2019-03-07 16:54
 */
public interface BaseService<T> {
    /**
     * 根据实体中的属性进行查询，只能有一个返回值，有多个结果是抛出异常，查询条件使用等号
     *
     * @param record
     * @return
     */
    T selectOne(T record);

    /**
     * 根据实体中的属性值进行查询，查询条件使用等号
     *
     * @param record
     * @return
     */
    List<T> select(T record);

    /**
     * 查询全部结果
     *
     * @return
     */
    List<T> selectAll();

    /**
     * 根据实体中的属性查询总数，查询条件使用等号
     *
     * @param record
     * @return
     */
    int selectCount(T record);

    /**
     * 根据主键字段进行查询，方法参数必须包含完整的主键属性，查询条件使用等号
     *
     * @param key
     * @return
     */
    T selectByPrimaryKey(Object key);

    /**
     * 根据主键字段查询是否存在，方法参数必须包含完整的主键属性，查询条件使用等号
     *
     * @param key 主键
     * @return true if exists or false.
     */
    boolean existsWithPrimaryKey(Object key);

    /**
     * 保存一个实体，null的属性也会保存，不会使用数据库默认值，不建议使用
     * 建议使用insertSelective()
     *
     * @param record
     * @return
     */
    int insert(T record);

    /**
     * 保存一个实体，null的属性不会保存，会使用数据库默认值
     *
     * @param record
     * @return
     */
    int insertSelective(T record);

    /**
     * 根据主键更新实体全部字段，null值会被更新
     *
     * @param record
     * @return
     */
    int updateByPrimaryKey(T record);

    /**
     * 根据主键更新属性不为null的值
     *
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(T record);

    /**
     * 根据实体属性作为条件进行删除，查询条件使用等号
     *
     * @param record
     * @return
     */
    int delete(T record);

    /**
     * 根据主键字段进行删除，方法参数必须包含完整的主键属性
     *
     * @param key
     * @return
     */
    int deleteByPrimaryKey(Object key);

    /**
     * 根据Example条件进行查询
     *
     * @param example
     * @return
     */
    List<T> selectByExample(Object example);

    /**
     * 根据Example条件进行查询
     *
     * @param example
     * @return
     */
    T selectOneByExample(Object example);

    /**
     * 根据Example条件进行查询总数
     *
     * @param example
     * @return
     */
    int selectCountByExample(Object example);

    /**
     * 根据Example条件删除数据
     *
     * @param example
     * @return
     */
    int deleteByExample(Object example);

    /**
     * 根据Example条件更新实体`record`包含的全部属性，null值会被更新
     *
     * @param record
     * @param example
     * @return
     */
    int updateByExample(@Param("record") T record, @Param("example") Object example);

    /**
     * 根据Example条件更新实体`record`包含的不是null的属性值
     *
     * @param record
     * @param example
     * @return
     */
    int updateByExampleSelective(@Param("record") T record, @Param("example") Object example);

    /**
     * 根据example条件和RowBounds进行分页查询
     *
     * @param example
     * @param rowBounds
     * @return
     */
    List<T> selectByExampleAndRowBounds(Object example, RowBounds rowBounds);

    /**
     * 根据实体属性和RowBounds进行分页查询
     *
     * @param record
     * @param rowBounds
     * @return
     */
    List<T> selectByRowBounds(T record, RowBounds rowBounds);

    /**
     * 带有乐观锁的删除方法，和delete()方法一样
     *
     * @param t
     * @return 影响的行数
     * @throws VersionException : 乐观锁版本号验证失败
     */
    int deleteWithVersion(T t);

    /**
     * 带有乐观锁的 deleteByPrimaryKey 方法
     *
     * @param o
     * @return 影响的行数
     * @throws VersionException: 乐观锁版本号验证失败
     */
    int deleteByPrimaryKeyWithVersion(Object o);


    /**
     * 带有乐观锁的 updateByPrimaryKey 方法
     *
     * @param t
     * @return 影响的行数
     * @throws VersionException: 乐观锁版本号验证失败
     */
    int updateByPrimaryKeyWithVersion(T t);

    /**
     * 带有乐观锁的 updateByPrimaryKeySelective 方法
     *
     * @param t
     * @return 影响的行数
     * @throws VersionException: 乐观锁版本号验证失败
     */
    int updateByPrimaryKeySelectiveWithVersion(T t);

    /**
     * 带有乐观锁的 updateByExample 方法
     *
     * @param t
     * @param o
     * @return 影响的行数
     * @throws VersionException: 乐观锁版本号验证失败
     */
    int updateByExampleWithVersion(T t, Object o);

    /**
     * 带有乐观锁的 updateByExampleSelective 方法
     *
     * @param t
     * @param o
     * @return 影响的行数
     * @throws VersionException: 乐观锁版本号验证失败
     */
    int updateByExampleSelectiveWithVersion(T t, Object o);

}
