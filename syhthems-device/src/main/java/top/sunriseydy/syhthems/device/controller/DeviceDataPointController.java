package top.sunriseydy.syhthems.device.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.sunriseydy.syhthems.common.exception.ServiceException;
import top.sunriseydy.syhthems.common.util.ResultUtils;
import top.sunriseydy.syhthems.common.vo.ResultVO;
import top.sunriseydy.syhthems.db.model.*;
import top.sunriseydy.syhthems.db.service.DataPointService;
import top.sunriseydy.syhthems.db.service.DeviceService;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author SunriseYDY
 * @date 2019-05-28 17:50
 */
@RestController
@RequestMapping("/device/data_point")
public class DeviceDataPointController {

    @Autowired
    DeviceService deviceService;

    @Autowired
    DataPointService dataPointService;

    @PostMapping
    public ResultVO insertDataPoint(HttpServletRequest request) {
        Map<String, String[]> params = request.getParameterMap();
        if (params.isEmpty()) {
            throw new ServiceException("请求参数为空");
        }
        // todo 将逻辑写到service中
        CustomDeviceUserDetails customDeviceUserDetails = deviceService.getCustomDeviceUserDetails();
        if (customDeviceUserDetails == null) {
            throw new ServiceException("未能获取到设备相关数据");
        }
        Device device = customDeviceUserDetails.getDevice();
        if (device == null) {
            throw new ServiceException("未能获取到设备相关数据");
        }
        Product product = customDeviceUserDetails.getProduct();
        if (product == null) {
            throw new ServiceException("未能获取到设备相关数据");
        }
        List<DataStream> dataStreams = customDeviceUserDetails.getDataStreams();
        if (CollectionUtils.isEmpty(dataStreams)) {
            throw new ServiceException("未能获取到设备相关数据");
        }
        for (String key : params.keySet()) {
            for (DataStream dataStream : dataStreams) {
                if (dataStream.getDataStreamCode().equals(key)) {
                    DataPoint dataPoint = new DataPoint();
                    dataPoint.setDataPointTimestamp(new Date());
                    dataPoint.setDeviceId(device.getDeviceId());
                    dataPoint.setDataStreamId(dataStream.getDataStreamId());
                    dataPoint.setDataPointCode(product.getProductId() + "," +
                            "data_stream_code=" +
                            dataStream.getDataStreamCode() +
                            "," + "device_id=" +
                            device.getDeviceId());
                    dataPoint.setDataPointData(params.get(key)[0]);
                    dataPointService.insertData(dataPoint);
                }
            }
        }
        return ResultUtils.success();
    }

}
