package top.sunriseydy.syhthems.db.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Table(name = "`product`")
public class Product extends BaseModel {
    /**
     * 产品id
     */
    @Id
    @Column(name = "`product_id`")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;

    /**
     * 产品关联的用户ID
     */
    @Column(name = "`user_id`")
    @NotNull
    private Long userId;

    /**
     * 产品名称
     */
    @Column(name = "`name`")
    @NotEmpty
    private String name;

    /**
     * 产品简介
     */
    @Column(name = "`description`")
    @Size(max = 256)
    private String description;

    public static final String PRODUCT_ID = "productId";

    public static final String DB_PRODUCT_ID = "product_id";

    public static final String USER_ID = "userId";

    public static final String DB_USER_ID = "user_id";

    public static final String NAME = "name";

    public static final String DB_NAME = "name";

    public static final String DESCRIPTION = "description";

    public static final String DB_DESCRIPTION = "description";

    public static final String CREATE_TIME = "createTime";

    public static final String DB_CREATE_TIME = "create_time";

    public static final String LAST_UPDATE_TIME = "lastUpdateTime";

    public static final String DB_LAST_UPDATE_TIME = "last_update_time";

    public static final String LAST_UPDATE_BY = "lastUpdateBy";

    public static final String DB_LAST_UPDATE_BY = "last_update_by";
}