package top.sunriseydy.syhthems.db.service;

import top.sunriseydy.syhthems.db.model.DataPoint;
import top.sunriseydy.syhthems.db.vo.DataPointQueryVO;

import java.util.List;

/**
 * @author SunriseYDY
 * @date 2019-05-24 11:02
 */
public interface DataPointService extends BaseService<DataPoint> {
    /**
     * 插入一个数据点
     * @param dataPoint
     */
    void insertData(DataPoint dataPoint);

    /**
     * 查询最新的一个数据点
     * @param dataPointQueryVO
     * @return
     */
    DataPoint selectTop(DataPointQueryVO dataPointQueryVO);

    /**
     * 根据条件查询总数
     * @param dataPointQueryVO
     * @return
     */
    int selectCountByQueryVO(DataPointQueryVO dataPointQueryVO);

    /**
     * 根据条件查询DataPoint
     * @param dataPointQueryVO
     * @return
     */
    List<DataPoint> selectByQueryVO(DataPointQueryVO dataPointQueryVO);

}
