package top.sunriseydy.syhthems.common.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author SunriseYDY
 * @date 2019-05-16 22:44
 */
@Getter
@Setter
@ConfigurationProperties(prefix = "syhthems.oauth2")
public class OAuthProperties {
    private String clientId;
    private String clientSecret;
}
