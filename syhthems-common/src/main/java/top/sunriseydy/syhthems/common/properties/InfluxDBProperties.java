package top.sunriseydy.syhthems.common.properties;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.NotEmpty;

/**
 * @author SunriseYDY
 * @date 2019-06-01 23:05
 */
@Getter
@Setter
public class InfluxDBProperties {
    @URL
    private String influxDbUrl;

    @NotEmpty
    private String adminToken;

}
