package top.sunriseydy.syhthems.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import top.sunriseydy.syhthems.common.enums.ResultEnum;
import top.sunriseydy.syhthems.common.util.ResultUtils;
import top.sunriseydy.syhthems.common.vo.ResultVO;
import top.sunriseydy.syhthems.db.model.DataPoint;
import top.sunriseydy.syhthems.db.model.DataStream;
import top.sunriseydy.syhthems.db.model.Device;
import top.sunriseydy.syhthems.db.service.DataPointService;
import top.sunriseydy.syhthems.db.service.DataStreamService;
import top.sunriseydy.syhthems.db.service.DeviceService;
import top.sunriseydy.syhthems.db.vo.DataPointQueryVO;
import top.sunriseydy.syhthems.web.vo.DataStreamVO;
import top.sunriseydy.syhthems.web.vo.DeviceVO;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author SunriseYDY
 * @date 2019-05-27 15:42
 */
@RestController
@RequestMapping("/web/api/device")
public class DeviceController extends BaseController {

    @Autowired
    DeviceService deviceService;

    @Autowired
    DataStreamService dataStreamService;

    @Autowired
    DataPointService dataPointService;

    @GetMapping
    public ResultVO getdevices(@RequestParam(required = true) Long productId) {
        return ResultUtils.success(deviceService.selectByProduct(productId));
    }

    @PostMapping
    public ResultVO addDevice(@Validated Device device) {
        device.setDeviceId(null);
        deviceService.insertDevice(device);
        return ResultUtils.success();
    }

    @GetMapping("/{did}")
    public ResultVO getDevice(@PathVariable(required = true, name = "did") Long deviceId) {
        Device device = deviceService.selectByPrimaryKey(deviceId);
        if (device == null) {
            return ResultUtils.error(ResultEnum.DEVICE_NOT_FOUND);
        }
        // List<DataStream> dataStreams = dataStreamService.selectByDeviceId(device.getDeviceId());
        List<DataStream> dataStreams = dataStreamService.selectByProductId(device.getProductId());
        if (dataStreams == null) {
            dataStreams = Collections.emptyList();
        }
        DeviceVO deviceVO = new DeviceVO();
        deviceVO.setDevice(device);
        List<DataStreamVO> dataStreamVOs = new ArrayList<>();
        for (DataStream dataStream : dataStreams) {
            DataStreamVO dataStreamVO = new DataStreamVO();
            dataStreamVO.setDataStream(dataStream);
            DataPointQueryVO dataPointQueryVO = new DataPointQueryVO(deviceId,
                    dataStream.getDataStreamId());
            List<DataPoint> dataPoints = dataPointService.selectByQueryVO(dataPointQueryVO);
            int total = dataPointService.selectCountByQueryVO(dataPointQueryVO);
            if (dataPoints == null) {
                dataPoints = Collections.emptyList();
            }
            dataStreamVO.setDataPoints(dataPoints);
            dataStreamVO.setTotalDataPoint(Long.valueOf(String.valueOf(total)));
            dataStreamVOs.add(dataStreamVO);
        }
        deviceVO.setDataStreamVOs(dataStreamVOs);
        return ResultUtils.success(deviceVO);
    }

    @PatchMapping("/{did}")
    public ResultVO updateDevice(@PathVariable(required = true, name = "did") Long deviceId,
                                 @Validated Device device) {
        device.setDeviceId(deviceId);
        deviceService.updateDevice(device);
        return ResultUtils.success();
    }

    @DeleteMapping("/{did}")
    public ResultVO deleteDevice(@PathVariable(required = true, name = "did") Long deviceId) {
        deviceService.deleteDeviceByPrimaryKey(deviceId);
        return ResultUtils.success();
    }

    @PostMapping("/{did}/bind_data_stream")
    public ResultVO bindDataStream(@PathVariable(required = true, name = "did") Long deviceId,
                                   @NotNull Long[] dataStreamIds) {
        // todo 此处可能有 bug
        deviceService.bindDeviceWithDataStream(deviceId, dataStreamIds);
        return ResultUtils.success();
    }
}
