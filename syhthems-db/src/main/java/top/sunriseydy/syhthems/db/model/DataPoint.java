package top.sunriseydy.syhthems.db.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Table(name = "`data_point`")
public class DataPoint extends BaseModel {
    /**
     * 数据点ID
     */
    @Id
    @Column(name = "`data_point_id`")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long dataPointId;

    /**
     * 数据点编码
     */
    @Column(name = "`data_point_code`")
    private String dataPointCode;

    /**
     * 数据点数据
     */
    @Column(name = "`data_point_data`")
    private String dataPointData;

    /**
     * 数据点时间戳
     */
    @Column(name = "`data_point_timestamp`")
    private Date dataPointTimestamp;

    /**
     * 数据流ID
     */
    @Column(name = "`data_stream_id`")
    private Long dataStreamId;

    /**
     * 设备ID
     */
    @Column(name = "`device_id`")
    private Long deviceId;

    public static final String DATA_POINT_ID = "dataPointId";

    public static final String DB_DATA_POINT_ID = "data_point_id";

    public static final String DATA_POINT_CODE = "dataPointCode";

    public static final String DB_DATA_POINT_CODE = "data_point_code";

    public static final String DATA_POINT_DATA = "dataPointData";

    public static final String DB_DATA_POINT_DATA = "data_point_data";

    public static final String DATA_POINT_TIMESTAMP = "dataPointTimestamp";

    public static final String DB_DATA_POINT_TIMESTAMP = "data_point_timestamp";

    public static final String DATA_STREAM_ID = "dataStreamId";

    public static final String DB_DATA_STREAM_ID = "data_stream_id";

    public static final String DEVICE_ID = "deviceId";

    public static final String DB_DEVICE_ID = "device_id";

    public static final String CREATE_TIME = "createTime";

    public static final String DB_CREATE_TIME = "create_time";

    public static final String LAST_UPDATE_TIME = "lastUpdateTime";

    public static final String DB_LAST_UPDATE_TIME = "last_update_time";

    public static final String LAST_UPDATE_BY = "lastUpdateBy";

    public static final String DB_LAST_UPDATE_BY = "last_update_by";
}