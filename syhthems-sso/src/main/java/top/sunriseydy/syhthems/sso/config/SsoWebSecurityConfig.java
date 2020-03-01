package top.sunriseydy.syhthems.sso.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import top.sunriseydy.syhthems.common.properties.CorsProperties;
import top.sunriseydy.syhthems.sso.handler.CustomAccessDeniedHandler;
import top.sunriseydy.syhthems.sso.handler.CustomAuthenticationEntryPoint;
import top.sunriseydy.syhthems.sso.handler.CustomAuthenticationFailureHandler;
import top.sunriseydy.syhthems.sso.handler.CustomAuthenticationSuccessHandler;
import top.sunriseydy.syhthems.sso.handler.CustomLogoutSuccessHandler;

/**
 * WebSecurity配置类
 *
 * @author SunriseYDY
 * @date 2019-03-18 14:44
 */
@Configuration
@EnableWebSecurity
public class SsoWebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    CorsProperties corsProperties;

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/static/**", "/favicon.ico");
    }

    // @Bean
    public CustomAuthenticationEntryPoint ssoCustomAuthenticationEntryPoint() {
        return new CustomAuthenticationEntryPoint();
    }

    // @Bean
    public CustomAccessDeniedHandler ssoCustomAccessDeniedHandler() {
        return new CustomAccessDeniedHandler();
    }

    // @Bean
    public AuthenticationSuccessHandler ssoCustomAuthenticationSuccessHandler() {
        return new CustomAuthenticationSuccessHandler();
    }

    // @Bean
    public AuthenticationFailureHandler ssoCustomAuthenticationFailureHandler() {
        return new CustomAuthenticationFailureHandler();
    }

    @Bean
    public LogoutSuccessHandler ssoCustomLogoutSuccessHandler() {
        return new CustomLogoutSuccessHandler();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Qualifier("customUserDetailsServiceImpl")
    @Autowired
    UserDetailsService userDetailsService;

    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = corsProperties.toCorsConfiguration();
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .headers()
                .httpStrictTransportSecurity().disable()
                .defaultsDisabled().cacheControl().and()
            .and()
                .authorizeRequests()
                    .antMatchers("/error",
                            "/actuator/*",
                            "/.well-known/*",
                            "/register",
                            "/login",
                            "/user/check_user_name",
                            "/user/check_user_email").permitAll()
                    .anyRequest().authenticated()
            .and()
                .formLogin()
                .loginPage("/login")
                .loginProcessingUrl("/login")
                // .failureHandler(ssoCustomAuthenticationFailureHandler())
                // .successHandler(ssoCustomAuthenticationSuccessHandler())
                .permitAll()
            .and()
                .logout().logoutSuccessHandler(ssoCustomLogoutSuccessHandler()).logoutUrl("/logout")
            .and()
                .csrf().disable()
            .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED);

    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService)
            .passwordEncoder(passwordEncoder);
    }
}
