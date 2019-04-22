package top.sunriseydy.syhthems.db.config;

import org.springframework.context.annotation.Configuration;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * Mapper扫描包配置类
 *
 * @author SunriseYDY
 * @date 2019-03-29 14:23
 */
@Configuration
@MapperScan("top.sunriseydy.syhthems.db.mapper")
public class MapperConfig {

}
