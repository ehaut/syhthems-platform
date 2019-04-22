package top.sunriseydy.syhthems.common.properties;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * security 相关配置
 *
 * @author SunriseYDY
 * @date 2019-04-09 16:21
 */
@Getter
@Setter
class SecurityProperties {
    /**
     * SSO 单点登录系统的地址
     * 默认值为 "http://localhost:8081"
     */
    @NotBlank
    private String ssoServer = "http://localhost:8081";
}
