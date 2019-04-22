package top.sunriseydy.syhthems.db.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import top.sunriseydy.syhthems.common.constants.BaseConstants;
import top.sunriseydy.syhthems.common.exception.ServiceException;
import top.sunriseydy.syhthems.db.model.Role;
import top.sunriseydy.syhthems.db.model.User;
import top.sunriseydy.syhthems.db.model.UserRole;
import top.sunriseydy.syhthems.db.service.*;
import top.sunriseydy.syhthems.db.util.BeanUtils;

import java.util.Date;

/**
 * @author SunriseYDY
 * @date 2019-03-26 15:31
 */
@Service
@Slf4j
@CacheConfig(cacheNames = "UserServiceImpl")
@Transactional(propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
public class UserServiceImpl extends BaseServiceImpl<User> implements UserService {

    @Autowired
    RoleService roleService;

    @Autowired
    UserRoleService userRoleService;

    @Autowired
    MenuService menuService;

    @Autowired
    RoleMenuService roleMenuService;

    /**
     * 根据用户名检测用户是否存在
     *
     * @param username
     * @return true if user with the username is exist.
     */
    @Override
    public Boolean existsWithUsername(String username) {
        return null != this.selectByUsername(username);
    }

    /**
     * 根据用户名来查询用户
     *
     * @param username
     * @return
     */
    @Override
    public User selectByUsername(String username) {
        User user = new User();
        user.setUsername(username);
        user = super.selectOne(user);
        return user;
    }

    /**
     * 处理用户注册
     *
     * @param newUser
     * @return newUser
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public User registerUser(User newUser) {
        Assert.notNull(newUser, "注册失败，用户为空");
        Assert.hasText(newUser.getUsername(), "注册失败，用户名为空");
        Assert.hasText(newUser.getPassword(), "注册失败，用户密码为空");
        Assert.isTrue(!this.existsWithUsername(newUser.getUsername()), "注册失败，用户名已存在");
        if (super.insertSelective(newUser) == 0) {
            throw new ServiceException("sorry，注册失败");
        }
        if (newUser == null || newUser.getUserId() == null) {
            throw new ServiceException("注册失败，无法获取用户id");
        }
        Role newUserRole = roleService.selectByRoleCode(BaseConstants.DEFAULT_USER_ROLE_CODE);
        if (newUserRole == null || newUserRole.getRoleId() == null) {
            throw new ServiceException("注册失败，获取默认角色失败");
        }

        UserRole newUserAndRole = new UserRole();
        newUserAndRole.setUserId(newUser.getUserId());
        newUserAndRole.setRoleId(newUserRole.getRoleId());
        if (userRoleService.insertSelective(newUserAndRole) == 0) {
            throw new ServiceException("注册失败，新用户关联角色失败");
        }
        return newUser;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @CacheEvict(cacheNames = "UserDetails",
            key = "'CustomUserDetailsServiceImpl:loadUserByUsername:(' + #user.getUsername() + ')'",
            cacheManager = "JDKCacheManager",
            condition = "#user != null && #user.getUsername() != null ")
    public int updateLogin(User user) {
        Assert.notNull(user, "用户不能为空");
        User u = new User();
        u.setUserId(user.getUserId());
        if (BaseConstants.YES.equals(user.getFirstLogin())) {
            u.setFirstLogin(BaseConstants.NO);
        }
        BeanUtils.copyObjectVersionValue(user, u);
        u.setLastLoginTime(new Date());
        return super.updateByPrimaryKeySelectiveWithVersion(u);
    }

    @Override
    @CacheEvict(cacheNames = "UserDetails",
            key = "'CustomUserDetailsServiceImpl:loadUserByUsername:(' + #username + ')'",
            condition = "#username != null",
            cacheManager = "JDKCacheManager")
    public void userLogout(String username) {
        log.debug("{} 登出", username);
    }
}
