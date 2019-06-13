package top.sunriseydy.syhthems.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.sunriseydy.syhthems.common.util.ResultUtils;
import top.sunriseydy.syhthems.common.vo.ResultVO;
import top.sunriseydy.syhthems.db.service.DataPointService;
import top.sunriseydy.syhthems.db.vo.DataPointQueryVO;

/**
 * @author SunriseYDY
 * @date 2019-06-02 03:43
 */
@RestController
@RequestMapping("/web/api/data_point")
public class WebApiDataPointController extends BaseController {
    @Autowired
    DataPointService dataPointService;

    @RequestMapping
    public ResultVO selectByQueryVO(@RequestBody DataPointQueryVO dataPointQueryVO) {
        return ResultUtils.success(dataPointService.selectByQueryVO(dataPointQueryVO));
    }

}
