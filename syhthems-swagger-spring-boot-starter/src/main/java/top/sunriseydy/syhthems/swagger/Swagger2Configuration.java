package top.sunriseydy.syhthems.swagger;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.swagger2.configuration.Swagger2DocumentationConfiguration;

/**
 * 引入 swagger 自带的配置
 *
 * @author SunriseYDY
 * @date 2020-04-15 15:23
 */
@Configuration
@Import({
        Swagger2DocumentationConfiguration.class,
        BeanValidatorPluginsConfiguration.class
})
public class Swagger2Configuration {

}
