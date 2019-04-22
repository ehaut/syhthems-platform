package top.sunriseydy.syhthems.db;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author SunriseYDY
 * @date 2019-04-08 16:27
 */
@SpringBootApplication(scanBasePackages = {"top.sunriseydy.syhthems"})
public class DB {
    public static void main(String[] args) {
        SpringApplication.run(DB.class, args);
    }
}
