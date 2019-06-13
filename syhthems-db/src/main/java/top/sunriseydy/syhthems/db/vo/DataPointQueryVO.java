package top.sunriseydy.syhthems.db.vo;

import lombok.Data;

import java.util.Date;

/**
 * @author SunriseYDY
 * @date 2019-05-24 12:21
 */
@Data
public class DataPointQueryVO {
    Long productId;

    Long deviceId;

    Long dataStreamId;

    Long userId;

    Date startDateTime;

    Date endDateTime;

    Integer rowsPerPage = 50;

    Integer page = 1;

    public DataPointQueryVO() {
    }

    public DataPointQueryVO(Long deviceId, Long dataStreamId) {
        this.deviceId = deviceId;
        this.dataStreamId = dataStreamId;
        this.startDateTime = new Date(0);
        this.endDateTime = new Date();
    }

    public DataPointQueryVO(Long deviceId, Long dataStreamId, Integer rowsPerPage, Integer page) {
        this.deviceId = deviceId;
        this.dataStreamId = dataStreamId;
        this.rowsPerPage = rowsPerPage;
        this.page = page;
        this.startDateTime = new Date(0);
        this.endDateTime = new Date();
    }

    public DataPointQueryVO(Long deviceId, Long dataStreamId, Date startDateTime, Date endDateTime, Integer rowsPerPage, Integer page) {
        this.deviceId = deviceId;
        this.dataStreamId = dataStreamId;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.rowsPerPage = rowsPerPage;
        this.page = page;
    }
}
