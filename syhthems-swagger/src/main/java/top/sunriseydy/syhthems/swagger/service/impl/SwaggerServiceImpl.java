package top.sunriseydy.syhthems.swagger.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import springfox.documentation.swagger.web.SwaggerResource;
import top.sunriseydy.syhthems.common.constants.ServiceConstants;
import top.sunriseydy.syhthems.common.vo.ResultVO;
import top.sunriseydy.syhthems.swagger.infra.RouteVO;
import top.sunriseydy.syhthems.swagger.infra.feign.GatewayFeign;
import top.sunriseydy.syhthems.swagger.service.SwaggerService;

import javax.validation.constraints.NotEmpty;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static top.sunriseydy.syhthems.common.constants.BaseConstants.API_DOC_PATH;
import static top.sunriseydy.syhthems.common.constants.BaseConstants.EMPTY_JSON;
import static top.sunriseydy.syhthems.swagger.infra.config.CustomSwaggerResourcesProvider.API_PATH_PREFIX;

/**
 * @author SunriseYDY
 * @date 2020-04-21 22:36
 */
@Service
@Slf4j
public class SwaggerServiceImpl implements SwaggerService {

    private final DiscoveryClient discoveryClient;

    private final GatewayFeign gatewayFeign;

    public SwaggerServiceImpl(DiscoveryClient discoveryClient, GatewayFeign gatewayFeign) {
        this.discoveryClient = discoveryClient;
        this.gatewayFeign = gatewayFeign;
    }

    @Override
    public String fetchServiceApiDoc(@NotEmpty String serviceName, @NotEmpty String servicePath) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            // 获取网关地址
            List<ServiceInstance> instances = discoveryClient.getInstances(ServiceConstants.SYHTHEMS_GATEWAY_NAME);
            Assert.notEmpty(instances, "获取网关地址失败 --- 无法获取已经注册的网关");
            String gatewayUrl = instances.get(0).getUri().toString();
            ResponseEntity<String> responseEntity = restTemplate.getForEntity(new URI(gatewayUrl + servicePath + API_DOC_PATH),
                    String.class);
            log.debug("获取{}服务接口文档成功", serviceName);
            if (HttpStatus.OK.equals(responseEntity.getStatusCode()) && responseEntity.hasBody()) {
                return responseEntity.getBody();
            } else {
                return EMPTY_JSON;
            }
        } catch (HttpStatusCodeException e) {
            log.error("获取{}服务文档失败：{}", serviceName, e.getStatusCode().toString());
            return EMPTY_JSON;
        } catch (Exception e) {
            log.error("获取" + serviceName + "服务文档出错：", e);
            return EMPTY_JSON;
        }
    }

    @Override
    public List<SwaggerResource> getSwaggerResource() {
        List<String> serviceIds = discoveryClient.getServices();
        if (CollectionUtils.isEmpty(serviceIds)) {
            return Collections.emptyList();
        }
        ResultVO<List<RouteVO>> response = gatewayFeign.getRoutes();
        List<RouteVO> routes = response.getData();
        if (CollectionUtils.isEmpty(routes)) {
            return Collections.emptyList();
        }
        List<SwaggerResource> resources = new ArrayList<>();

        for (String serviceId : serviceIds) {
            RouteVO routeVO = routes.stream()
                    .filter(routeVO1 -> serviceId.equals(routeVO1.getLocation()))
                    .findFirst()
                    .orElse(null);
            if (routeVO == null) {
                continue;
            }
            if (!checkApiIsEnable(serviceId, routeVO.getFullPath().replace("/**", ""))) {
                continue;
            }
            resources.add(convertToSwaggerResource(serviceId, routeVO.getFullPath().replace("/**", "")));

        }
        return resources;
    }

    @Override
    @Validated
    public Boolean checkApiIsEnable(@NotEmpty String serviceName, @NotEmpty String servicePath) {
        String json = this.fetchServiceApiDoc(serviceName, servicePath);
        return json != null && !EMPTY_JSON.equals(json);
    }

    @Validated
    private SwaggerResource convertToSwaggerResource(@NotEmpty String serviceName, @NotEmpty String servicePath) {
        SwaggerResource swaggerResource = new SwaggerResource();
        swaggerResource.setName(serviceName);
        swaggerResource.setUrl(API_PATH_PREFIX + "/" + serviceName + servicePath);
        swaggerResource.setSwaggerVersion("2.0");
        return swaggerResource;
    }
}
