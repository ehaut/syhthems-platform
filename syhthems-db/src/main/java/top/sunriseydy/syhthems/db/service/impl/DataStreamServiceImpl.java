package top.sunriseydy.syhthems.db.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;
import top.sunriseydy.syhthems.common.exception.ServiceException;
import top.sunriseydy.syhthems.db.model.DataStream;
import top.sunriseydy.syhthems.db.model.DeviceDataStream;
import top.sunriseydy.syhthems.db.service.DataStreamService;
import top.sunriseydy.syhthems.db.service.DeviceDataStreamService;
import top.sunriseydy.syhthems.db.service.DeviceService;
import top.sunriseydy.syhthems.db.util.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author SunriseYDY
 * @date 2019-05-24 14:52
 */
@Service
@CacheConfig(cacheNames = "DataStreamServiceImpl")
@Transactional(propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
public class DataStreamServiceImpl extends BaseServiceImpl<DataStream> implements DataStreamService {
    @Autowired
    DeviceDataStreamService deviceDataStreamService;

    @Autowired
    DeviceService deviceService;

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void insertDataStream(DataStream dataStream) {
        Assert.notNull(dataStream, "数据流不能为空");
        if (dataStream.getProductId() == null) {
            throw new ServiceException("数据流中的产品id不能为空");
        }
        dataStream.setDataStreamId(null);
        if (super.insertSelective(dataStream) == 0) {
            throw new ServiceException("添加数据流失败");
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void deleteDataStreamByPrimaryKey(Long dataStreamId) {
        Assert.notNull(dataStreamId, "数据流不能为空");
        // todo 检查是否绑定的有设备，有则不允许删除
        // todo 删除redis缓存
        super.deleteByPrimaryKeyWithVersion(dataStreamId);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void deleteByProductId(Long productId) {
        Assert.notNull(productId, "产品ID不能为空");
        DataStream dataStream = new DataStream();
        dataStream.setProductId(productId);
        // todo 检测数据流是否绑定有设备，如果有则禁止删除。
        // todo 删除redis缓存
        super.delete(dataStream);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void updateDataStream(DataStream dataStream) {
        Assert.notNull(dataStream, "数据流不能为空");
        DataStream old = super.selectByPrimaryKey(dataStream.getDataStreamId());
        BeanUtils.copyObjectVersionValue(old, dataStream);
        super.updateByPrimaryKeySelectiveWithVersion(dataStream);
    }

    @Override
    public List<DataStream> selectByDeviceId(Long deviceId) {
        if (deviceId == null) {
            throw new ServiceException("deviceId 为空");
        }
        DeviceDataStream deviceDataStream = new DeviceDataStream();
        deviceDataStream.setDeviceId(deviceId);
        List<DeviceDataStream> deviceDataStreams = deviceDataStreamService.select(deviceDataStream);
        if (CollectionUtils.isEmpty(deviceDataStreams)) {
            return null;
        }
        Example dataStreamExample = new Example(DataStream.class);
        dataStreamExample.createCriteria().andIn(DataStream.DATA_STREAM_ID,
                deviceDataStreams.stream().map(DeviceDataStream::getDataStreamId).collect(Collectors.toList()));
        return super.selectByExample(dataStreamExample);
    }

    @Override
    public List<DataStream> selectByProductId(Long productId) {
        if (productId == null) {
            throw new ServiceException("产品ID不能为空");
        }
        DataStream dataStream = new DataStream();
        dataStream.setProductId(productId);
        return super.select(dataStream);
    }
}
