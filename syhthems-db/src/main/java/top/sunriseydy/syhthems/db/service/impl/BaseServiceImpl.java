package top.sunriseydy.syhthems.db.service.impl;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.version.VersionException;
import top.sunriseydy.syhthems.db.base.CustomBaseMapper;
import top.sunriseydy.syhthems.db.service.BaseService;

import java.util.List;

/**
 * 通用服务实现类. 通过通用mapper提供基本的增删改查功能。
 * 增删改方法的事务传播特性为REQUIRED，其余的查询方法则为SUPPORTS
 *
 * @author SunriseYDY
 * @date 2019-03-07 16:55
 */
@Transactional(propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
public abstract class BaseServiceImpl<T> implements BaseService<T> {

    @Autowired
    private CustomBaseMapper<T> customBaseMapper;

    /**
     * 根据实体中的属性进行查询，只能有一个返回值，有多个结果是抛出异常，查询条件使用等号
     *
     * @param record
     * @return
     */
    @Override
    public T selectOne(T record) {
        return customBaseMapper.selectOne(record);
    }

    /**
     * 根据实体中的属性值进行查询，查询条件使用等号
     *
     * @param record
     * @return
     */
    @Override
    public List<T> select(T record) {
        return customBaseMapper.select(record);
    }

    /**
     * 查询全部结果
     *
     * @return
     */
    @Override
    public List<T> selectAll() {
        return customBaseMapper.selectAll();
    }

    /**
     * 根据实体中的属性查询总数，查询条件使用等号
     *
     * @param record
     * @return
     */
    @Override
    public int selectCount(T record) {
        return customBaseMapper.selectCount(record);
    }

    /**
     * 根据主键字段进行查询，方法参数必须包含完整的主键属性，查询条件使用等号
     *
     * @param key
     * @return
     */
    @Override
    public T selectByPrimaryKey(Object key) {
        return customBaseMapper.selectByPrimaryKey(key);
    }

    /**
     * 根据主键字段查询总数，方法参数必须包含完整的主键属性，查询条件使用等号
     *
     * @param key
     * @return
     */
    @Override
    public boolean existsWithPrimaryKey(Object key) {
        return customBaseMapper.existsWithPrimaryKey(key);
    }

    /**
     * 保存一个实体，null的属性也会保存，不会使用数据库默认值
     *
     * @param record
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int insert(T record) {
        return customBaseMapper.insert(record);
    }

    /**
     * 保存一个实体，null的属性不会保存，会使用数据库默认值
     *
     * @param record
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int insertSelective(T record) {
        return customBaseMapper.insertSelective(record);
    }

    /**
     * 根据主键更新实体全部字段，null值会被更新
     *
     * @param record
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int updateByPrimaryKey(T record) {
        return customBaseMapper.updateByPrimaryKey(record);
    }

    /**
     * 根据主键更新属性不为null的值
     *
     * @param record
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int updateByPrimaryKeySelective(T record) {
        return customBaseMapper.updateByPrimaryKeySelective(record);
    }

    /**
     * 根据实体属性作为条件进行删除，查询条件使用等号
     *
     * @param record
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int delete(T record) {
        return customBaseMapper.delete(record);
    }

    /**
     * 根据主键字段进行删除，方法参数必须包含完整的主键属性
     *
     * @param key
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int deleteByPrimaryKey(Object key) {
        return customBaseMapper.deleteByPrimaryKey(key);
    }

    /**
     * 根据Example条件进行查询
     *
     * @param example
     * @return
     */
    @Override
    public List<T> selectByExample(Object example) {
        return customBaseMapper.selectByExample(example);
    }

    /**
     * 根据Example条件进行查询
     *
     * @param example
     * @return
     */
    @Override
    public T selectOneByExample(Object example) {
        return customBaseMapper.selectOneByExample(example);
    }

    /**
     * 根据Example条件进行查询总数
     *
     * @param example
     * @return
     */
    @Override
    public int selectCountByExample(Object example) {
        return customBaseMapper.selectCountByExample(example);
    }

    /**
     * 根据Example条件删除数据
     *
     * @param example
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int deleteByExample(Object example) {
        return customBaseMapper.deleteByExample(example);
    }

    /**
     * 根据Example条件更新实体`record`包含的全部属性，null值会被更新
     *
     * @param record
     * @param example
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int updateByExample(T record, Object example) {
        return customBaseMapper.updateByExample(record, example);
    }

    /**
     * 根据Example条件更新实体`record`包含的不是null的属性值
     *
     * @param record
     * @param example
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int updateByExampleSelective(T record, Object example) {
        return customBaseMapper.updateByExampleSelective(record, example);
    }

    /**
     * 根据example条件和RowBounds进行分页查询
     *
     * @param example
     * @param rowBounds
     * @return
     */
    @Override
    public List<T> selectByExampleAndRowBounds(Object example, RowBounds rowBounds) {
        return customBaseMapper.selectByExampleAndRowBounds(example, rowBounds);
    }

    /**
     * 根据实体属性和RowBounds进行分页查询
     *
     * @param record
     * @param rowBounds
     * @return
     */
    @Override
    public List<T> selectByRowBounds(T record, RowBounds rowBounds) {
        return customBaseMapper.selectByRowBounds(record, rowBounds);
    }

    /**
     * 带有乐观锁的删除方法，和delete()方法一样
     *
     * @param t
     * @return 影响的行数
     * @throws VersionException : 乐观锁版本号验证失败
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int deleteWithVersion(T t) {
        return customBaseMapper.deleteWithVersion(t);
    }

    /**
     * 带有乐观锁的 deleteByPrimaryKey 方法
     *
     * @param o
     * @return 影响的行数
     * @throws VersionException: 乐观锁版本号验证失败
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int deleteByPrimaryKeyWithVersion(Object o) {
        return customBaseMapper.deleteByPrimaryKeyWithVersion(o);
    }

    /**
     * 带有乐观锁的 updateByPrimaryKey 方法
     *
     * @param t
     * @return 影响的行数
     * @throws VersionException: 乐观锁版本号验证失败
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int updateByPrimaryKeyWithVersion(T t) {
        return customBaseMapper.updateByPrimaryKeyWithVersion(t);
    }

    /**
     * 带有乐观锁的 updateByPrimaryKeySelective 方法
     *
     * @param t
     * @return 影响的行数
     * @throws VersionException: 乐观锁版本号验证失败
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int updateByPrimaryKeySelectiveWithVersion(T t) {
        return customBaseMapper.updateByPrimaryKeySelectiveWithVersion(t);
    }

    /**
     * 带有乐观锁的 updateByExample 方法
     *
     * @param t
     * @param o
     * @return 影响的行数
     * @throws VersionException: 乐观锁版本号验证失败
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int updateByExampleWithVersion(T t, Object o) {
        return customBaseMapper.updateByExampleWithVersion(t, o);
    }

    /**
     * 带有乐观锁的 updateByExampleSelective 方法
     *
     * @param t
     * @param o
     * @return 影响的行数
     * @throws VersionException: 乐观锁版本号验证失败
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int updateByExampleSelectiveWithVersion(T t, Object o) {
        return customBaseMapper.updateByExampleSelectiveWithVersion(t, o);
    }
}
