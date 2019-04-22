package top.sunriseydy.syhthems.common.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import top.sunriseydy.syhthems.common.properties.SyhthemsProperties;

/**
 * @author SunriseYDY
 * @date 2019-04-09 16:15
 */
@Configuration
@EnableConfigurationProperties(SyhthemsProperties.class)
public class PropertyConfig {
}
