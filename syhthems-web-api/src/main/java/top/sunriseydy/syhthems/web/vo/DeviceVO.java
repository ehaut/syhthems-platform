package top.sunriseydy.syhthems.web.vo;

import lombok.Data;
import top.sunriseydy.syhthems.db.model.Device;

import java.util.List;

/**
 * @author SunriseYDY
 * @date 2019-05-30 07:43
 */
@Data
public class DeviceVO {
    /**
     * 该设备
     */
    private Device device;

    /**
     * 该设备绑定的数据流
     */
    private List<DataStreamVO> dataStreamVOs;
}
