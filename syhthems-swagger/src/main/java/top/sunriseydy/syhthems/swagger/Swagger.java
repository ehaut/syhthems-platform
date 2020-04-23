package top.sunriseydy.syhthems.swagger;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.autoconfigure.security.servlet.ManagementWebSecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author SunriseYDY
 * @date 2020-04-16 14:41
 */
@SpringBootApplication(exclude = {SecurityAutoConfiguration.class, ManagementWebSecurityAutoConfiguration.class}, scanBasePackages = {"top.sunriseydy.syhthems"})
@EnableEurekaClient
@EnableSyhthemsSwagger
@EnableFeignClients(basePackages = "top.sunriseydy.syhthems")
public class Swagger {
    public static void main(String[] args) {
        SpringApplication.run(Swagger.class, args);
    }
}
