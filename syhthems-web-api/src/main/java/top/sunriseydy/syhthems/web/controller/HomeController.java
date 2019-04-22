package top.sunriseydy.syhthems.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.sunriseydy.syhthems.common.util.ResultUtils;
import top.sunriseydy.syhthems.common.vo.ResultVO;
import top.sunriseydy.syhthems.db.util.UserUtils;

/**
 * @author SunriseYDY
 * @date 2019-04-15 10:11
 */
@RestController
public class HomeController {
    @Autowired
    UserUtils userUtils;

    @RequestMapping(value = "/")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResultVO home() {
        return ResultUtils.success(userUtils.getCustomUserDetailsFromSecurityContextHolderWithUsername());
    }
}
