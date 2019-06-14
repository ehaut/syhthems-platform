package top.sunriseydy.syhthems.device.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import top.sunriseydy.syhthems.common.constants.BaseConstants;

/**
 * @author SunriseYDY
 * @date 2019-05-28 16:51
 */
@Configuration
@EnableWebSecurity
@Order(BaseConstants.DEFAULT_ORDER + 300)
public class DeviceWebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    @Qualifier("customDeviceUserDetailsServiceImpl")
    UserDetailsService customDeviceUserDetailsServiceImpl;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.headers().disable()
                .csrf().disable()
                .httpBasic()
                .and()
                .authorizeRequests().antMatchers("/device/**").hasRole("DEVICE")
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.NEVER);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customDeviceUserDetailsServiceImpl)
            .passwordEncoder(new PasswordEncoder() {
                @Override
                public String encode(CharSequence rawPassword) {
                    return rawPassword.toString();
                }

                @Override
                public boolean matches(CharSequence rawPassword, String encodedPassword) {
                    return encodedPassword.equals(rawPassword.toString());
                }
            });
    }
}
