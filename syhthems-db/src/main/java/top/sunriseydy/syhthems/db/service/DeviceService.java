package top.sunriseydy.syhthems.db.service;

import top.sunriseydy.syhthems.db.model.CustomDeviceUserDetails;
import top.sunriseydy.syhthems.db.model.Device;

import java.util.List;

/**
 * @author SunriseYDY
 * @date 2019-05-21 15:25
 */
public interface DeviceService extends BaseService<Device> {

    /**
     * 根据产品列表来查询设备
     * @param products 产品ID数组
     * @return List<Device>
     */
    List<Device> selectByProducts(Long[] products);

    /**
     * 根据产品查询设备
     * @param productId
     * @return
     */
    List<Device> selectByProduct(Long productId);

    void insertDevice(Device device);

    void updateDevice(Device device);

    /**
     * 根据Device ID 删除Device
     * <p>包括删除设备下关联的数据流和数据点</p>
     * @param deviceId
     */
    void deleteDeviceByPrimaryKey(Long deviceId);

    /**
     * 根据产品ID来删除其所属的设备，以及设备关联的数据流和数据点。
     * @param productId
     */
    void deleteDevicesByProduceId(Long productId);

    /**
     * 绑定设备和数据流
     * @param deviceId
     * @param dataStreamIds
     */
    void bindDeviceWithDataStream(Long deviceId, Long ... dataStreamIds);

    CustomDeviceUserDetails getCustomDeviceUserDetails();
}
