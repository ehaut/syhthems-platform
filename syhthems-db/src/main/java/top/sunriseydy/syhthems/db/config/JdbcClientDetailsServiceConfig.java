package top.sunriseydy.syhthems.db.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;

import javax.sql.DataSource;

/**
 * JdbcClientDetailsService 配置类
 *
 * 注册为Bean
 *
 * @author SunriseYDY
 * @date 2019-04-23 20:43
 */
@Configuration
public class JdbcClientDetailsServiceConfig {
    private final DataSource dataSource;

    private final PasswordEncoder passwordEncoder;

    public JdbcClientDetailsServiceConfig(PasswordEncoder passwordEncoder, DataSource dataSource) {
        this.passwordEncoder = passwordEncoder;
        this.dataSource = dataSource;
    }

    @Bean
    @Primary
    public ClientDetailsService jdbcClientDetailsService() {
        JdbcClientDetailsService clientDetailsService = new JdbcClientDetailsService(dataSource);
        clientDetailsService.setPasswordEncoder(passwordEncoder);
        return clientDetailsService;
    }
}
