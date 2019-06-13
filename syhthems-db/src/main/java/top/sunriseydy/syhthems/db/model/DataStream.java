package top.sunriseydy.syhthems.db.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@Table(name = "`data_stream`")
public class DataStream extends BaseModel {
    /**
     * 数据流ID
     */
    @Id
    @Column(name = "`data_stream_id`")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long dataStreamId;

    /**
     * 产品id
     */
    @Column(name = "`product_id`")
    @NotNull
    private Long productId;

    /**
     * 数据流编码,即设备上传数据的参数名
     */
    @Column(name = "`data_stream_code`")
    @NotEmpty
    private String dataStreamCode;

    /**
     * 数据流的单位
     */
    @Column(name = "`unit`")
    @NotEmpty
    private String unit;

    /**
     * 数据流的单位
     */
    @Column(name = "`unit_symbol`")
    @NotEmpty
    private String unitSymbol;

    public static final String DATA_STREAM_ID = "dataStreamId";

    public static final String DB_DATA_STREAM_ID = "data_stream_id";

    public static final String PRODUCT_ID = "productId";

    public static final String DB_PRODUCT_ID = "product_id";

    public static final String DATA_STREAM_CODE = "dataStreamCode";

    public static final String DB_DATA_STREAM_CODE = "data_stream_code";

    public static final String UNIT = "unit";

    public static final String DB_UNIT = "unit";

    public static final String UNIT_SYMBOL = "unitSymbol";

    public static final String DB_UNIT_SYMBOL = "unit_symbol";

    public static final String CREATE_TIME = "createTime";

    public static final String DB_CREATE_TIME = "create_time";

    public static final String LAST_UPDATE_TIME = "lastUpdateTime";

    public static final String DB_LAST_UPDATE_TIME = "last_update_time";

    public static final String LAST_UPDATE_BY = "lastUpdateBy";

    public static final String DB_LAST_UPDATE_BY = "last_update_by";
}