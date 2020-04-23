package top.sunriseydy.syhthems.swagger.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;
import top.sunriseydy.syhthems.common.util.ResultUtils;
import top.sunriseydy.syhthems.common.vo.ResultVO;
import top.sunriseydy.syhthems.swagger.infra.RouteVO;
import top.sunriseydy.syhthems.swagger.infra.feign.GatewayFeign;
import top.sunriseydy.syhthems.swagger.service.SwaggerService;

import java.util.List;

import static top.sunriseydy.syhthems.swagger.infra.config.CustomSwaggerResourcesProvider.API_PATH_PREFIX;

/**
 * @author SunriseYDY
 * @date 2020-04-20 14:22
 */
@RestController
@Slf4j
@Api(tags = "swagger 接口")
public class SwaggerController {
    private final SwaggerService swaggerService;

    public SwaggerController(SwaggerService swaggerService) {
        this.swaggerService = swaggerService;
    }

    @GetMapping(value = API_PATH_PREFIX + "/{serviceName}" + "/{servicePathWithoutPrefix}")
    @ApiOperation("获取指定服务的 swagger api 文档数据")
    public String fetchServiceApiDoc(@ApiParam(value = "服务名称", example = "syhthems-swagger", required = true) @PathVariable String serviceName,
                                     @ApiParam(value = "服务的路由路径,不带前缀'/'", example = "swagger", required = true) @PathVariable String servicePathWithoutPrefix) {
        return swaggerService.fetchServiceApiDoc(serviceName, "/" + servicePathWithoutPrefix);
    }
}
