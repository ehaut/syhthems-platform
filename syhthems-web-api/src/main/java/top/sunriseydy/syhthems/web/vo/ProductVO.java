package top.sunriseydy.syhthems.web.vo;

import lombok.Data;
import top.sunriseydy.syhthems.db.model.DataStream;
import top.sunriseydy.syhthems.db.model.Device;
import top.sunriseydy.syhthems.db.model.Product;

import java.util.List;

/**
 * 产品VO，用于前台数据显示
 *
 * @author SunriseYDY
 * @date 2019-05-30 07:41
 */
@Data
public class ProductVO {
    /**
     * 该产品
     */
    private Product product;

    /**
     * 该产品下的设备
     */
    private List<Device> devices;

    /**
     * 该产品下的数据流
     */
    private List<DataStream> dataStreams;
}
