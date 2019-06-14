package top.sunriseydy.syhthems.device;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author SunriseYDY
 * @date 2019-05-28 16:44
 */
@SpringBootApplication(scanBasePackages = {"top.sunriseydy.syhthems"})
public class Device {
    public static void main(String[] args) {
        SpringApplication.run(Device.class, args);
    }
}
