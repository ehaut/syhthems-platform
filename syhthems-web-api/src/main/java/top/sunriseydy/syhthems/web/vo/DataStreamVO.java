package top.sunriseydy.syhthems.web.vo;

import lombok.Data;
import top.sunriseydy.syhthems.db.model.DataPoint;
import top.sunriseydy.syhthems.db.model.DataStream;

import java.util.List;

/**
 * @author SunriseYDY
 * @date 2019-06-02 10:19
 */
@Data
public class DataStreamVO {

    private DataStream dataStream;

    private List<DataPoint> dataPoints;

    private Long totalDataPoint;
}
