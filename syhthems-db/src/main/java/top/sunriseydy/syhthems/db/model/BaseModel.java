package top.sunriseydy.syhthems.db.model;

import com.fasterxml.jackson.annotation.JsonFilter;
import lombok.Data;
import tk.mybatis.mapper.annotation.Version;
import top.sunriseydy.syhthems.db.mapper.version.DateNextVersion;

import javax.persistence.Column;
import java.io.Serializable;
import java.util.Date;

/**
 * Model类的基类，包含基本字段等
 *
 * @author SunriseYDY
 * @date 2019-03-05 17:06
 */
@Data
@JsonFilter("cache")
public class BaseModel implements Serializable {

    public static final String CREATE_TIME = "createTime";
    public static final String DB_CREATE_TIME = "create_time";
    public static final String LAST_UPDATE_TIME = "lastUpdateTime";
    public static final String DB_LAST_UPDATE_TIME = "last_update_time";
    public static final String LAST_UPDATE_BY = "lastUpdateBy";
    public static final String DB_LAST_UPDATE_BY = "last_update_by";

    @Column(updatable = false, name = "create_time")
    private Date createTime;

    @Column(name = "last_update_time")
    @Version(nextVersion = DateNextVersion.class)
    private Date lastUpdateTime;

    @Column(name = "last_update_by")
    private Long lastUpdateBy;

}