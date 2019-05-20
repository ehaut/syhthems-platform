package top.sunriseydy.syhthems.web.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import top.sunriseydy.syhthems.common.properties.CorsProperties;
import top.sunriseydy.syhthems.web.converter.CustomJwtAuthenticationConverter;

/**
 * WebSecurity 配置
 *
 * @author SunriseYDY
 * @date 2019-04-11 10:45
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    CorsProperties corsProperties;

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/static/**", "/favicon.ico");
    }

    /**
     * CORS 配置类
     * <p>Reference: https://docs.spring.io/spring-security/site/docs/current/reference/htmlsingle/#cors</p>
     * @return corsConfigurationSource
     */
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = corsProperties.toCorsConfiguration();
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    CustomJwtAuthenticationConverter customJwtAuthenticationConverter() {
        return new CustomJwtAuthenticationConverter();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors()
                .configurationSource(corsConfigurationSource())
            .and()
                .authorizeRequests()
                    .antMatchers("/login", "/error", "/oauth/token").permitAll()
                    .anyRequest().authenticated()
            .and()
                .csrf().disable()
            .oauth2ResourceServer().jwt().jwtAuthenticationConverter(customJwtAuthenticationConverter());
    }
}
