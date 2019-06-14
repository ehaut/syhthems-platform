package top.sunriseydy.syhthems.sso.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import top.sunriseydy.syhthems.common.util.ResultUtils;
import top.sunriseydy.syhthems.common.vo.ResultVO;

/**
 * @author SunriseYDY
 * @date 2019-03-12 20:22
 */
@Controller
@RequestMapping("/sso")
public class SsoHomeController {

    @RequestMapping(value = "/")
    @ResponseBody
    public ResultVO home() {
        return ResultUtils.success("这里什么都没有");
    }
}
