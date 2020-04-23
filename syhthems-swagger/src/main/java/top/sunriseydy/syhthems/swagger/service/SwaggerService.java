package top.sunriseydy.syhthems.swagger.service;

import springfox.documentation.swagger.web.SwaggerResource;

import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * @author SunriseYDY
 * @date 2020-04-21 22:33
 */
public interface SwaggerService {
    /**
     * 从服务中获取 API 文档
     * @param serviceName 服务名称 例如 "syhthems-swagger"
     * @param servicePath 服务的路由路径 例如 "/swagger"
     * @return 如果获取成功则返回 API 文档信息的 json， 获取失败则返回空 json
     */
    String fetchServiceApiDoc(@NotEmpty String serviceName, @NotEmpty String servicePath);

    /**
     * 获取 SwaggerResource 列表
     * @return
     */
    List<SwaggerResource> getSwaggerResource();

    /**
     * 检查该服务是否启用了 swagger
     * @param serviceName 服务名称 例如 "syhthems-swagger"
     * @param servicePath 服务的路由路径 例如 "/swagger"
     * @return true or false
     */
    Boolean checkApiIsEnable(@NotEmpty String serviceName, @NotEmpty String servicePath);
}
