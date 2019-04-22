package top.sunriseydy.syhthems.sso;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author SunriseYDY
 * @date 2019-03-12 20:20
 */
@SpringBootApplication(scanBasePackages = {"top.sunriseydy.syhthems"})
public class Sso {
    public static void main(String[] args) {
        SpringApplication.run(Sso.class, args);
    }
}