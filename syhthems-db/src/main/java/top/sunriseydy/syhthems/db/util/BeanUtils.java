package top.sunriseydy.syhthems.db.util;

import org.springframework.util.Assert;
import top.sunriseydy.syhthems.db.model.BaseModel;

/**
 * 自定义的Bean工具类
 *
 * @author SunriseYDY
 * @date 2019-04-18 11:09
 */
public class BeanUtils {
    /**
     * 将源对象中的版本号值拷贝到目标对象中
     * <p>用于更新或删除一个Model时， new 一个新的Model后将旧的Model的版本号值转给新的Model
     * <p>版本号属性为lastUpdateTime
     *
     * @param <T>        BaseModel的子类
     * @param sourceBean
     * @param targetBean
     */
    public static <T extends BaseModel> void copyObjectVersionValue(T sourceBean, T targetBean) {
        Assert.notNull(sourceBean, "源对象不能为空");
        Assert.notNull(sourceBean.getLastUpdateTime(), "基础字段 " + BaseModel.LAST_UPDATE_TIME + "不能为空");
        Assert.notNull(targetBean, "目标对象不能为空");
        targetBean.setLastUpdateTime(sourceBean.getLastUpdateTime());
    }
}
