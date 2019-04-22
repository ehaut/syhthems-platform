package top.sunriseydy.syhthems.db.base;

import tk.mybatis.mapper.annotation.RegisterMapper;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;
import tk.mybatis.mapper.version.VersionException;

/**
 * CustomBaseMapper，所有Mapper的基类，实现了部分带有乐观锁的删除和修改方法
 *
 * @author SunriseYDY
 * @date 2019-03-06 09:16
 */
@RegisterMapper
public interface CustomBaseMapper<T> extends Mapper<T>, MySqlMapper<T> {

    /**
     * 带有乐观锁的删除方法，和delete()方法一样
     *
     * @param t record
     * @return 影响的行数
     * @throws VersionException: 乐观锁版本号验证失败
     */
    default int deleteWithVersion(T t) {
        int result = delete(t);
        if (result == 0) {
            throw new VersionException("删除失败");
        }
        return result;
    }

    /**
     * 带有乐观锁的 deleteByPrimaryKey 方法
     *
     * @param o Primary Key
     * @return 影响的行数
     * @throws VersionException: 乐观锁版本号验证失败
     */
    default int deleteByPrimaryKeyWithVersion(Object o) {
        int result = deleteByPrimaryKey(o);
        if (result == 0) {
            throw new VersionException("删除失败");
        }
        return result;
    }

    /**
     * 带有乐观锁的 updateByPrimaryKey 方法
     *
     * @param t record
     * @return 影响的行数
     * @throws VersionException: 乐观锁版本号验证失败
     */
    default int updateByPrimaryKeyWithVersion(T t) {
        int result = updateByPrimaryKey(t);
        if (result == 0) {
            throw new VersionException("更新失败");
        }
        return result;
    }

    /**
     * 带有乐观锁的 updateByPrimaryKeySelective 方法
     *
     * @param t record
     * @return 影响的行数
     * @throws VersionException: 乐观锁版本号验证失败
     */
    default int updateByPrimaryKeySelectiveWithVersion(T t) {
        int result = updateByPrimaryKeySelective(t);
        if (result == 0) {
            throw new VersionException("更新失败");
        }
        return result;
    }

    /**
     * 带有乐观锁的 updateByExample 方法
     *
     * @param t record
     * @param o example
     * @return 影响的行数
     * @throws VersionException: 乐观锁版本号验证失败
     */
    default int updateByExampleWithVersion(T t, Object o) {
        int result = updateByExample(t, o);
        if (result == 0) {
            throw new VersionException("更新失败");
        }
        return result;
    }

    /**
     * 带有乐观锁的 updateByExampleSelective 方法
     *
     * @param t record
     * @param o example
     * @return 影响的行数
     * @throws VersionException: 乐观锁版本号验证失败
     */
    default int updateByExampleSelectiveWithVersion(T t, Object o) {
        int result = updateByExampleSelective(t, o);
        if (result == 0) {
            throw new VersionException("更新失败");
        }
        return result;
    }
}
