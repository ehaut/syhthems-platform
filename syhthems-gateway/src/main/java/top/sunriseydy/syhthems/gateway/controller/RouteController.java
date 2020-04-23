package top.sunriseydy.syhthems.gateway.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.netflix.zuul.filters.Route;
import org.springframework.cloud.netflix.zuul.filters.discovery.DiscoveryClientRouteLocator;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import top.sunriseydy.syhthems.common.util.ResultUtils;
import top.sunriseydy.syhthems.common.vo.ResultVO;

import java.util.List;

/**
 * @author SunriseYDY
 * @date 2020-04-23 21:04
 */
@RestController
@Slf4j
public class RouteController {
    private final DiscoveryClientRouteLocator routeLocator;

    public RouteController(DiscoveryClientRouteLocator routeLocator) {
        this.routeLocator = routeLocator;
    }

    /**
     * 获取路由信息列表
     * @return List&lt;{@link Route}&gt;
     */
    @GetMapping("/routes")
    public ResultVO<List<Route>> getRoutes() {
        return ResultUtils.success(routeLocator.getRoutes());
    }
}
