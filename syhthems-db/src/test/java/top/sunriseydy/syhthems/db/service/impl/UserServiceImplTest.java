package top.sunriseydy.syhthems.db.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import top.sunriseydy.syhthems.db.enums.UserStatusEnum;
import top.sunriseydy.syhthems.db.model.User;
import top.sunriseydy.syhthems.db.service.UserService;

import java.io.IOException;

/**
 * @author SunriseYDY
 * @date 2019-04-08 16:12
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class UserServiceImplTest {

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    UserService userService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Test
    public void selectByUsername() throws IOException {
        User user = userService.selectByUsername("sunriseydy");
        Assert.assertNotNull(user);
        log.info(user.toString());
        String json = objectMapper.writeValueAsString(user);
        log.warn(json);
        user = objectMapper.readValue(json, User.class);
        log.warn(user.toString());
        // System.out.println(passwordEncoder.matches("syhthems", user.getPassword()));
    }

    @Test
    @Transactional
    public void registerUser() {
        User user = new User();
        user.setUsername("sunriseydy-test");
        user.setEmail("syhthems@mail.sunriseydy.top");
        user.setPassword(passwordEncoder.encode("syhthems"));
        user.setUserStatus(UserStatusEnum.VALIDE_STATUS);
        user = userService.registerUser(user);
        Assert.assertNotNull(user);
        log.info(user.toString());
        user = userService.selectByUsername("sunriseydy");
        Assert.assertNotNull(user);
        log.info(user.toString());
    }
}