package top.sunriseydy.syhthems.db.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;
import top.sunriseydy.syhthems.common.exception.ServiceException;
import top.sunriseydy.syhthems.db.model.CustomDeviceUserDetails;
import top.sunriseydy.syhthems.db.model.DataPoint;
import top.sunriseydy.syhthems.db.model.Device;
import top.sunriseydy.syhthems.db.model.DeviceDataStream;
import top.sunriseydy.syhthems.db.service.DataPointService;
import top.sunriseydy.syhthems.db.service.DeviceDataStreamService;
import top.sunriseydy.syhthems.db.service.DeviceService;
import top.sunriseydy.syhthems.db.util.BeanUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author SunriseYDY
 * @date 2019-05-22 22:46
 */
@Service
@CacheConfig(cacheNames = "DeviceServiceImpl")
@Transactional(propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
public class DeviceServiceImpl extends BaseServiceImpl<Device> implements DeviceService {

    @Autowired
    DeviceDataStreamService deviceDataStreamService;

    @Autowired
    DataPointService dataPointService;

    @Override
    public List<Device> selectByProducts(Long[] products) {
        if (products == null || products.length == 0) {
            throw new ServiceException("产品为空");
        }
        Example deviceExample = new Example(Device.class);
        deviceExample.createCriteria().andIn(Device.PRODUCT_ID,
                Arrays.asList(products));
        return super.selectByExample(deviceExample);
    }

    @Override
    public List<Device> selectByProduct(Long productId) {
        if (productId == null) {
            throw new ServiceException("产品ID为空");
        }
        Device device = new Device();
        device.setProductId(productId);
        return super.select(device);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void insertDevice(Device device) {
        Assert.notNull(device, "设备不能为空");
        device.setDeviceId(null);
        if (super.insertSelective(device) == 0) {
            throw new ServiceException("添加设备失败");
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void updateDevice(Device device) {
        Assert.notNull(device, "设备不能为空");
        if (device.getDeviceId() == null) {
            throw new ServiceException("产品ID为空");
        }
        Device old = super.selectByPrimaryKey(device.getDeviceId());
        BeanUtils.copyObjectVersionValue(old, device);
        super.updateByPrimaryKeySelectiveWithVersion(device);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void deleteDeviceByPrimaryKey(Long deviceId) {
        if (deviceId == null) {
            throw new ServiceException("产品ID为空");
        }
        super.deleteByPrimaryKeyWithVersion(deviceId);
        // 删除设备下关联的数据流和数据点
        DeviceDataStream deviceDataStream = new DeviceDataStream();
        deviceDataStream.setDeviceId(deviceId);
        deviceDataStreamService.delete(deviceDataStream);

        DataPoint dataPoint = new DataPoint();
        dataPoint.setDeviceId(deviceId);
        dataPointService.delete(dataPoint);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void deleteDevicesByProduceId(Long productId) {
        if (productId == null) {
            throw new ServiceException("产品ID为空");
        }

        Device device = new Device();
        device.setProductId(productId);
        List<Device> devices = super.select(device);
        if (CollectionUtils.isEmpty(devices)) {
            return;
        }
        // 删除设备
        super.delete(device);

        // 删除设备绑定的数据流
        Example deviceDataStreamExample = new Example(DeviceDataStream.class);
        deviceDataStreamExample.createCriteria().andIn(DeviceDataStream.DEVICE_ID,
                devices.stream().map(Device::getDeviceId).collect(Collectors.toList()));
        if (deviceDataStreamService.deleteByExample(deviceDataStreamExample) == 0) {
            throw new ServiceException("删除设备失败");
        }
        // 删除数据点
        Example dataPointExample = new Example(DataPoint.class);
        dataPointExample.createCriteria().andIn(DataPoint.DEVICE_ID,
                devices.stream().map(Device::getDeviceId).collect(Collectors.toList()));
        dataPointService.deleteByExample(dataPointExample);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void bindDeviceWithDataStream(Long deviceId, Long... dataStreamIds) {
        if (deviceId == null) {
            throw new ServiceException("deviceId不能为空");
        }
        if (dataStreamIds.length == 0) {
            throw new ServiceException("dataStreamIds不能为空");
        }
        for (Long dataStreamId : dataStreamIds) {
            DeviceDataStream deviceDataStream = new DeviceDataStream();
            deviceDataStream.setDeviceId(deviceId);
            deviceDataStream.setDataStreamId(dataStreamId);
            deviceDataStreamService.insertSelective(deviceDataStream);
        }
    }

    @Override
    public CustomDeviceUserDetails getCustomDeviceUserDetails() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof UsernamePasswordAuthenticationToken) {
            Object userDetails = authentication.getPrincipal();
            if (userDetails instanceof CustomDeviceUserDetails) {
                return ((CustomDeviceUserDetails) userDetails);
            }
            return null;
        }
        return null;
    }
}
