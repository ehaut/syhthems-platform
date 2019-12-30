package top.sunriseydy.syhthems.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

/**
 * @author SunriseYDY
 * @date 2019-12-30 23:31
 */
@SpringBootApplication
@EnableZuulProxy
public class Gateway {
    public static void main(String[] args) {
        SpringApplication.run(Gateway.class, args);
    }
}
