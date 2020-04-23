package top.sunriseydy.syhthems.web.config;

import org.springframework.beans.factory.annotation.Autowired;
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

import static top.sunriseydy.syhthems.common.constants.BaseConstants.API_DOC_PATH;

/**
 * WebSecurity 配置
 *
 * @author SunriseYDY
 * @date 2019-04-11 10:45
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebApiWebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    CorsProperties corsProperties;

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/static/**", "/favicon.ico");
    }

    /**
     * CORS 配置类
     * <p>Reference: https://docs.spring.io/spring-security/site/docs/current/reference/htmlsingle/#cors</p>
     *
     * @return webApiCorsConfigurationSource
     */
    // @Bean
    CorsConfigurationSource webApiCorsConfigurationSource() {
        CorsConfiguration configuration = corsProperties.toCorsConfiguration();
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/web/api/**", configuration);
        return source;
    }

    // @Bean
    CustomJwtAuthenticationConverter webApiCustomJwtAuthenticationConverter() {
        return new CustomJwtAuthenticationConverter();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/error",
                        API_DOC_PATH,
                        "/actuator/*").permitAll()
                .anyRequest().authenticated()
                .and()
                .oauth2ResourceServer().jwt().jwtAuthenticationConverter(webApiCustomJwtAuthenticationConverter())
                .and().and()
                .csrf().disable();
    }
}
