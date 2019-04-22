package top.sunriseydy.syhthems.db.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

/**
 * @author SunriseYDY
 * @date 2019-04-16 16:54
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class CustomUserDetailsServiceImplTest {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    ObjectMapper objectMapper;

    @Qualifier("customUserDetailsServiceImpl")
    @Autowired
    UserDetailsService userDetailsService;

    @Test
    public void loadUserByUsername() {
        UserDetails userDetails = userDetailsService.loadUserByUsername("sunriseydy");
        try {
            String json = objectMapper.writeValueAsString(userDetails);
            log.warn(json);
            UserDetails jsonUserDetails = objectMapper.readValue(json, userDetails.getClass());
            log.warn(String.valueOf(jsonUserDetails == null));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}