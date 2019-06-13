package top.sunriseydy.syhthems.db.service.impl;

import org.apache.ibatis.session.RowBounds;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;
import top.sunriseydy.syhthems.common.exception.ServiceException;
import top.sunriseydy.syhthems.db.model.DataPoint;
import top.sunriseydy.syhthems.db.service.DataPointService;
import top.sunriseydy.syhthems.db.vo.DataPointQueryVO;

import java.util.Date;
import java.util.List;

/**
 * @author SunriseYDY
 * @date 2019-05-24 11:03
 */
@SuppressWarnings("Duplicates")
@Service
@CacheConfig(cacheNames = "DataPointServiceImpl")
@Transactional(propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
public class DataPointServiceImpl extends BaseServiceImpl<DataPoint> implements DataPointService {
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void insertData(DataPoint dataPoint) {
        Assert.notNull(dataPoint, "数据点不能为空");
        dataPoint.setDataPointId(null);
        if (dataPoint.getDataPointCode() == null) {
            throw new ServiceException("数据点编码不能为空");
        }
        if (dataPoint.getDataPointData() == null) {
            throw new ServiceException("数据点数据不能为空");
        }
        if (dataPoint.getDataPointTimestamp() == null) {
            dataPoint.setDataPointTimestamp(new Date());
        }
        if (dataPoint.getDeviceId() == null) {
            throw new ServiceException("数据点设备ID不能为空");
        }
        if (super.insertSelective(dataPoint) == 0) {
            throw new ServiceException("数据点插入失败");
        }
    }

    @Override
    public DataPoint selectTop(DataPointQueryVO dataPointQueryVO) {
        Assert.notNull(dataPointQueryVO, "查询参数不能为空");
        Assert.notNull(dataPointQueryVO.getDeviceId(), "设备ID不能为空");
        Assert.notNull(dataPointQueryVO.getDataStreamId(), "数据流参数不能为空");
        Example dataPointExample = new Example(DataPoint.class);
        dataPointExample.createCriteria().andEqualTo(DataPoint.DEVICE_ID, dataPointQueryVO.getDeviceId())
                .andEqualTo(DataPoint.DATA_STREAM_ID, dataPointQueryVO.getDataStreamId());
        dataPointExample.orderBy(DataPoint.DATA_POINT_TIMESTAMP).desc();
        List<DataPoint> dataPoints = super.selectByExampleAndRowBounds(dataPointExample, new RowBounds(1, 1));
        if (CollectionUtils.isEmpty(dataPoints)) {
            return null;
        }
        return dataPoints.get(0);
    }

    public List<DataPoint> selectNew(DataPointQueryVO dataPointQueryVO) {
        Assert.notNull(dataPointQueryVO, "查询参数不能为空");
        Assert.notNull(dataPointQueryVO.getDeviceId(), "设备ID不能为空");
        Assert.notNull(dataPointQueryVO.getDataStreamId(), "数据流参数不能为空");
        Assert.notNull(dataPointQueryVO.getStartDateTime(), "起始时间不能为空");
        Assert.notNull(dataPointQueryVO.getEndDateTime(), "结束时间不能为空");
        Example dataPointExample = new Example(DataPoint.class);
        dataPointExample.createCriteria().andEqualTo(DataPoint.DEVICE_ID, dataPointQueryVO.getDeviceId())
                .andEqualTo(DataPoint.DATA_STREAM_ID, dataPointQueryVO.getDataStreamId())
                .andBetween(DataPoint.DATA_POINT_TIMESTAMP, dataPointQueryVO.getStartDateTime(), dataPointQueryVO.getEndDateTime());
        dataPointExample.orderBy(DataPoint.DATA_POINT_TIMESTAMP).desc();
        return super.selectByExample(dataPointExample);
    }

    @Override
    public int selectCountByQueryVO(DataPointQueryVO dataPointQueryVO) {
        Assert.notNull(dataPointQueryVO, "查询参数不能为空");
        Assert.notNull(dataPointQueryVO.getDeviceId(), "设备ID不能为空");
        Assert.notNull(dataPointQueryVO.getDataStreamId(), "数据流参数不能为空");
        Assert.notNull(dataPointQueryVO.getStartDateTime(), "起始时间不能为空");
        Assert.notNull(dataPointQueryVO.getEndDateTime(), "结束时间不能为空");
        Example dataPointExample = new Example(DataPoint.class);
        dataPointExample.createCriteria().andEqualTo(DataPoint.DEVICE_ID, dataPointQueryVO.getDeviceId())
                .andEqualTo(DataPoint.DATA_STREAM_ID, dataPointQueryVO.getDataStreamId())
                .andBetween(DataPoint.DATA_POINT_TIMESTAMP, dataPointQueryVO.getStartDateTime(), dataPointQueryVO.getEndDateTime());
        return super.selectCountByExample(dataPointExample);
    }

    @Override
    public List<DataPoint> selectByQueryVO(DataPointQueryVO dataPointQueryVO) {
        Assert.notNull(dataPointQueryVO, "查询参数不能为空");
        Assert.notNull(dataPointQueryVO.getDeviceId(), "设备ID不能为空");
        Assert.notNull(dataPointQueryVO.getDataStreamId(), "数据流参数不能为空");
        Assert.notNull(dataPointQueryVO.getStartDateTime(), "起始时间不能为空");
        Assert.notNull(dataPointQueryVO.getEndDateTime(), "结束时间不能为空");
        Example dataPointExample = new Example(DataPoint.class);
        dataPointExample.createCriteria().andEqualTo(DataPoint.DEVICE_ID, dataPointQueryVO.getDeviceId())
                .andEqualTo(DataPoint.DATA_STREAM_ID, dataPointQueryVO.getDataStreamId())
                .andBetween(DataPoint.DATA_POINT_TIMESTAMP, dataPointQueryVO.getStartDateTime(), dataPointQueryVO.getEndDateTime());
        RowBounds rowBounds = new RowBounds(dataPointQueryVO.getPage(), dataPointQueryVO.getRowsPerPage());
        return super.selectByExampleAndRowBounds(dataPointExample, rowBounds);
    }
}
