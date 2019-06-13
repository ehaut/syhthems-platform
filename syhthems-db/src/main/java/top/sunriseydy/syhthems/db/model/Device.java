package top.sunriseydy.syhthems.db.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Table(name = "`device`")
public class Device extends BaseModel {
    /**
     * 设备id
     */
    @Id
    @Column(name = "`device_id`")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long deviceId;

    /**
     * 设备密钥
     */
    @Column(name = "`device_secret`")
    @NotEmpty
    private String deviceSecret;

    /**
     * 设备关联的产品ID
     */
    @Column(name = "`product_id`")
    @NotNull
    private Long productId;

    /**
     * 设备编号
     */
    @Column(name = "`code`")
    @NotEmpty
    private String code;

    /**
     * 设备名称
     */
    @Column(name = "`name`")
    @NotEmpty
    private String name;

    /**
     * 设备简介
     */
    @Column(name = "`description`")
    @Size(max = 256)
    private String description;

    /**
     * 设备标签，逗号分隔的字符串
     */
    @Column(name = "`tags`")
    private String tags;

    public static final String DEVICE_ID = "deviceId";

    public static final String DB_DEVICE_ID = "device_id";

    public static final String DEVICE_SECRET = "deviceSecret";

    public static final String DB_DEVICE_SECRET = "device_secret";

    public static final String PRODUCT_ID = "productId";

    public static final String DB_PRODUCT_ID = "product_id";

    public static final String CODE = "code";

    public static final String DB_CODE = "code";

    public static final String NAME = "name";

    public static final String DB_NAME = "name";

    public static final String DESCRIPTION = "description";

    public static final String DB_DESCRIPTION = "description";

    public static final String TAGS = "tags";

    public static final String DB_TAGS = "tags";

    public static final String CREATE_TIME = "createTime";

    public static final String DB_CREATE_TIME = "create_time";

    public static final String LAST_UPDATE_TIME = "lastUpdateTime";

    public static final String DB_LAST_UPDATE_TIME = "last_update_time";

    public static final String LAST_UPDATE_BY = "lastUpdateBy";

    public static final String DB_LAST_UPDATE_BY = "last_update_by";
}