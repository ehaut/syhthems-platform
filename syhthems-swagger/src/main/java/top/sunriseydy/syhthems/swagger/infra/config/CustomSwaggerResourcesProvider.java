package top.sunriseydy.syhthems.swagger.infra.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;
import top.sunriseydy.syhthems.swagger.service.SwaggerService;

import java.util.List;

/**
 * swagger resource 配置
 *
 * <p>
 *     用于从注册中心获取所有注册的服务并生成各自的 swagger resource 分组信息
 * </p>
 *
 * @author SunriseYDY
 * @date 2020-04-16 15:17
 */
@Component
@Primary
@Slf4j
public class CustomSwaggerResourcesProvider implements SwaggerResourcesProvider {

    public static final String API_PATH_PREFIX = "/doc";

    private final SwaggerService swaggerService;

    public CustomSwaggerResourcesProvider(SwaggerService swaggerService) {
        this.swaggerService = swaggerService;
    }

    @Override
    public List<SwaggerResource> get() {
        return swaggerService.getSwaggerResource();
    }
}
