package top.sunriseydy.syhthems.db.service.impl;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import top.sunriseydy.syhthems.db.model.Menu;
import top.sunriseydy.syhthems.db.model.Role;
import top.sunriseydy.syhthems.db.model.User;
import top.sunriseydy.syhthems.db.service.MenuService;
import top.sunriseydy.syhthems.db.service.RoleService;
import top.sunriseydy.syhthems.db.service.UserService;
import top.sunriseydy.syhthems.db.util.UserUtils;

import java.util.List;

/**
 * 自定义的UserDetailsService
 *
 * @author SunriseYDY
 * @date 2019-03-09 23:11
 */
@Configuration
@Service
public class CustomUserDetailsServiceImpl implements UserDetailsService {

    private final
    UserService userService;

    private final
    RoleService roleService;

    private final
    MenuService menuService;

    public CustomUserDetailsServiceImpl(UserService userService, RoleService roleService, MenuService menuService) {
        this.userService = userService;
        this.roleService = roleService;
        this.menuService = menuService;
    }

    @Override
    @Cacheable(cacheNames = "UserDetails",
            unless = "#username == null",
            cacheManager = "JDKCacheManager")
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Assert.notNull(username, "用户名不能为空");
        User user = userService.selectByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("用户不存在");
        }

        List<Role> roles = roleService.selectByUser(user);
        Assert.notEmpty(roles, "用户没有被分配角色");

        List<Menu> menus = menuService.selectByRoles(roles);
        Assert.notEmpty(menus, "角色没有被分配菜单");

        return UserUtils.convertToUserDetails(user, roles, menus);
    }
}
