package top.sunriseydy.syhthems.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import top.sunriseydy.syhthems.common.util.ResultUtils;
import top.sunriseydy.syhthems.common.vo.ResultVO;
import top.sunriseydy.syhthems.db.model.DataStream;
import top.sunriseydy.syhthems.db.service.DataStreamService;

import java.util.List;

/**
 * @author SunriseYDY
 * @date 2019-05-28 11:41
 */
@RestController
@RequestMapping("/web/api/ds")
public class DataStreamController extends BaseController {
    @Autowired
    DataStreamService dataStreamService;

    @GetMapping
    public ResultVO getDataStreamList(@RequestParam(required = true) Long productId) {
        List<DataStream> dataStreams = dataStreamService.selectByProductId(productId);
        return ResultUtils.success(dataStreams);
    }

    @GetMapping("/{ds_id}")
    public ResultVO getDataStream(@PathVariable(required = true, name = "ds_id") Long dataStreamId) {
        DataStream dataStream = dataStreamService.selectByPrimaryKey(dataStreamId);
        return ResultUtils.success(dataStream);
    }

    @GetMapping("/did/{did}")
    public ResultVO getDataStreamByDeviceId(@PathVariable(required = true, name = "did") Long deviceId) {
        return ResultUtils.success(dataStreamService.selectByDeviceId(deviceId));
    }

    @PostMapping
    public ResultVO addDataStream(@Validated DataStream dataStream) {
        dataStreamService.insertDataStream(dataStream);
        return ResultUtils.success();
    }

    @PatchMapping("/{ds_id}")
    public ResultVO updateDataStream(@PathVariable(required = true, name = "ds_id") Long dataStreamId,
                                     @Validated DataStream dataStream) {
        dataStream.setDataStreamId(dataStreamId);
        dataStreamService.updateDataStream(dataStream);
        return ResultUtils.success();
    }

    @DeleteMapping("/{ds_id}")
    public ResultVO deleteByDataStreamId(@PathVariable(required = true, name = "ds_id") Long dataStreamId) {
        dataStreamService.deleteDataStreamByPrimaryKey(dataStreamId);
        return ResultUtils.success();
    }
}
