package top.sunriseydy.syhthems.common.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;

/**
 * syhthems 配置
 *
 * @author SunriseYDY
 * @date 2019-04-09 15:37
 */
@ConfigurationProperties(prefix = "syhthems")
@Validated
@Getter
@Setter
public class SyhthemsProperties {
    @Valid
    private SecurityProperties security;
}
