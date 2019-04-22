package top.sunriseydy.syhthems.db.mapper.version;

import tk.mybatis.mapper.version.NextVersion;
import tk.mybatis.mapper.version.VersionException;

import java.util.Date;

/**
 * 定义支持 java.util.Date 的 NextVersion 实现
 *
 * @author SunriseYDY
 * @date 2019-03-06 14:34
 */
public class DateNextVersion implements NextVersion {
    @Override
    public Object nextVersion(Object current) throws VersionException {
        if (current == null) {
            throw new VersionException("当前版本号为空!");
        }
        if (current instanceof Date) {
            return new Date();
        } else {
            throw new VersionException("只支持 java.util.Date 类型的 version 字段");
        }
    }
}
