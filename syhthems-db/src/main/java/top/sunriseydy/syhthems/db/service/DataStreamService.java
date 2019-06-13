package top.sunriseydy.syhthems.db.service;

import top.sunriseydy.syhthems.db.model.DataStream;

import java.util.List;

/**
 * @author SunriseYDY
 * @date 2019-05-21 15:26
 */
public interface DataStreamService  extends BaseService<DataStream> {
    /**
     * 插入一个DataStream
     * @param dataStream
     */
    void insertDataStream(DataStream dataStream);

    /**
     * 删除一个DataStream
     * @param dataStreamId
     */
    void deleteDataStreamByPrimaryKey(Long dataStreamId);

    void deleteByProductId(Long productId);

    /**
     * 更新一个DataStream
     * @param dataStream
     */
    void updateDataStream(DataStream dataStream);

    List<DataStream> selectByDeviceId(Long deviceId);

    List<DataStream> selectByProductId(Long productId);
}
