package top.sunriseydy.syhthems.swagger.infra.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import top.sunriseydy.syhthems.common.constants.ServiceConstants;
import top.sunriseydy.syhthems.common.vo.ResultVO;
import top.sunriseydy.syhthems.swagger.infra.RouteVO;

import java.util.List;

/**
 * @author SunriseYDY
 * @date 2020-04-17 15:31
 */
@FeignClient(name = ServiceConstants.SYHTHEMS_GATEWAY_NAME)
public interface GatewayFeign {
    /**
     * 获取路由信息列表
     * @return List&lt;{@link RouteVO}&gt;
     */
    @GetMapping("/routes")
    ResultVO<List<RouteVO>> getRoutes();
}
