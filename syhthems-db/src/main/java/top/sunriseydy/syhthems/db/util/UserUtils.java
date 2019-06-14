package top.sunriseydy.syhthems.db.util;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import top.sunriseydy.syhthems.db.model.CustomUserDetails;
import top.sunriseydy.syhthems.db.model.Menu;
import top.sunriseydy.syhthems.db.model.Role;
import top.sunriseydy.syhthems.db.model.User;

import java.util.ArrayList;
import java.util.List;

/**
 * 用户工具类
 *
 * @author SunriseYDY
 * @date 2019-03-28 17:28
 */
@Component
public class UserUtils {
    private final UserDetailsService userDetailsService;

    public UserUtils(@Qualifier("customUserDetailsServiceImpl") UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }


    /**
     * 将用户对象、角色对象、菜单对象整合转换为UserDetails对象
     *
     * @param user  该用户对象， 参见{@link User}
     * @param roles 该用户所拥有的角色对象list， 参见{@link Role}
     * @param menus 该用户的角色所拥有的菜单对象list， 参见{@link Menu}
     * @return 自定义的UserDetails， 参见{@link CustomUserDetails}
     */
    public static UserDetails convertToUserDetails(User user, List<Role> roles, List<Menu> menus) {
        Assert.notNull(user, "用户不能为空");
        Assert.hasText(user.getUsername(), "用户名不能为空");
        Assert.notEmpty(roles, "用户角色不能为空");
        Assert.notEmpty(menus, "角色权限不能为空");

        List<GrantedAuthority> authorities = new ArrayList<>();
        // 将用户的角色添加到权限中
        roles.forEach(role -> authorities.add(new SimpleGrantedAuthority(role.getRoleCode())));

        // 将角色所拥有的权限添加到权限中
        menus.stream().map(Menu::getPermission).distinct().forEach(s -> {
            if (s != null && !"ROLE_USER".equals(s)) {
                authorities.add(new SimpleGrantedAuthority(s));
            }
        });
        return new CustomUserDetails(
                user.getUsername(),
                user.getPassword(),
                authorities,
                user,
                roles,
                menus
        );
    }

    /**
     * 将自定义的CustomeUserDetailes对象转化为User对象
     *
     * @param userDetails {@link CustomUserDetails}
     * @return {@link User}
     */
    public static User convertToUser(CustomUserDetails userDetails) {
        Assert.notNull(userDetails, "userDetails 不能为空");
        return userDetails.getUser();
    }

    /**
     * 将自定义的CustomeUserDetailes对象转化为Role对象List
     *
     * @param userDetails {@link CustomUserDetails}
     * @return {@link Role}
     */
    public static List<Role> convertToRoles(CustomUserDetails userDetails) {
        Assert.notNull(userDetails, "userDetails 不能为空");
        return userDetails.getRoles();
    }

    /**
     * 将自定义的CustomeUserDetailes对象转化为Menu对象List
     *
     * @param userDetails {@link CustomUserDetails}
     * @return {@link Menu}
     */
    public static List<Menu> convertToMenus(CustomUserDetails userDetails) {
        Assert.notNull(userDetails, "userDetails 不能为空");
        return userDetails.getMenus();
    }

    /**
     * 判断用户是否已经登录
     * 通过权限里是否有用户默认的权限来判断。不能通过 isAuthenticated 方法判断，因为匿名用户也会返回true。
     *
     * @return
     */
    public static boolean isLogin() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication != null && authentication.isAuthenticated() && authentication.getAuthorities()
                .contains(new SimpleGrantedAuthority("ROLE_USER"));
    }

    /**
     * 从Spring Security Context中获取当前的 CustomUserDetails 对象。
     * 若获得的对象不是 CustomUserDetails 实例，则返回 null
     *
     * @return CustomUserDetails
     */
    public static CustomUserDetails getCustomUserDetails() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof AnonymousAuthenticationToken) {
            return null;
        }
        if (authentication instanceof UsernamePasswordAuthenticationToken) {
            Object userDetails = authentication.getPrincipal();
            if (userDetails instanceof CustomUserDetails) {
                return ((CustomUserDetails) userDetails).erasePassword();
            }
            return null;
        }
        return null;
    }

    /**
     * 从 Spring Security Context中获取 username 再获取 CustomUserDetails，若找不到则返回 null
     *
     * @return
     */
    public CustomUserDetails getCustomUserDetailsFromSecurityContextHolderWithUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            return null;
        }
        String username;
        if (authentication instanceof JwtAuthenticationToken) {
            username = ((JwtAuthenticationToken) authentication).getTokenAttributes().get("user_name").toString();
        } else {
            username = authentication.getName();
        }
        try {
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            if (userDetails instanceof CustomUserDetails) {
                return ((CustomUserDetails) userDetails).erasePassword();
            }
            return null;
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    /**
     * 得到当前用户的用户名
     *
     * @return username or null
     */
    public static String getUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            return null;
        } else if (authentication instanceof JwtAuthenticationToken) {
            return ((JwtAuthenticationToken) authentication).getTokenAttributes().get("user_name").toString();
        } else {
            return authentication.getName();
        }
    }
}
