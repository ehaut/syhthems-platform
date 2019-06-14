package top.sunriseydy.syhthems.device.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.sunriseydy.syhthems.common.util.ResultUtils;
import top.sunriseydy.syhthems.common.vo.ResultVO;
import top.sunriseydy.syhthems.db.model.CustomDeviceUserDetails;
import top.sunriseydy.syhthems.db.model.DataStream;
import top.sunriseydy.syhthems.db.service.DeviceService;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author SunriseYDY
 * @date 2019-06-01 23:23
 */
@RestController
@RequestMapping("/device")
public class DeviceHomeController {
    @Autowired
    DeviceService deviceService;

    @GetMapping("/")
    public ResultVO home() {
        CustomDeviceUserDetails customDeviceUserDetails = deviceService.getCustomDeviceUserDetails();
        List<DataStream> dataStreams = customDeviceUserDetails.getDataStreams();
        String dss = dataStreams.stream().map(DataStream::getDataStreamCode).collect(Collectors.joining(","));
        return ResultUtils.success(dss);
    }
}
