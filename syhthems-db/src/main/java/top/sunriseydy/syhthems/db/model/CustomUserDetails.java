package top.sunriseydy.syhthems.db.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
import java.util.List;

/**
 * 自定义的UserDetails,继承{@link User}
 *
 * @author SunriseYDY
 * @date 2019-03-08 11:01
 */
public class CustomUserDetails extends User {

    private final top.sunriseydy.syhthems.db.model.User user;

    private final List<Role> roles;

    private final List<Menu> menus;

    public CustomUserDetails(String username, String password, Collection<? extends GrantedAuthority> authorities, top.sunriseydy.syhthems.db.model.User user, List<Role> roles, List<Menu> menus) {
        super(username, password, authorities);
        this.user = user;
        this.roles = roles;
        this.menus = menus;
    }

    public CustomUserDetails(String username, String password, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities, top.sunriseydy.syhthems.db.model.User user, List<Role> roles, List<Menu> menus) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
        this.user = user;
        this.roles = roles;
        this.menus = menus;
    }

    public top.sunriseydy.syhthems.db.model.User getUser() {
        return user;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public List<Menu> getMenus() {
        return menus;
    }

    public CustomUserDetails erasePassword() {
        this.user.setPassword(null);
        super.eraseCredentials();
        return this;
    }
}
