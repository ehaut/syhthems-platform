package top.sunriseydy.syhthems.db.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Table(name = "`device_data_stream`")
public class DeviceDataStream extends BaseModel {
    /**
     * ID
     */
    @Id
    @Column(name = "`dds_id`")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ddsId;

    /**
     * 设备id
     */
    @Column(name = "`device_id`")
    private Long deviceId;

    /**
     * 数据流id
     */
    @Column(name = "`data_stream_id`")
    private Long dataStreamId;

    public static final String DDS_ID = "ddsId";

    public static final String DB_DDS_ID = "dds_id";

    public static final String DEVICE_ID = "deviceId";

    public static final String DB_DEVICE_ID = "device_id";

    public static final String DATA_STREAM_ID = "dataStreamId";

    public static final String DB_DATA_STREAM_ID = "data_stream_id";

    public static final String CREATE_TIME = "createTime";

    public static final String DB_CREATE_TIME = "create_time";

    public static final String LAST_UPDATE_TIME = "lastUpdateTime";

    public static final String DB_LAST_UPDATE_TIME = "last_update_time";

    public static final String LAST_UPDATE_BY = "lastUpdateBy";

    public static final String DB_LAST_UPDATE_BY = "last_update_by";
}