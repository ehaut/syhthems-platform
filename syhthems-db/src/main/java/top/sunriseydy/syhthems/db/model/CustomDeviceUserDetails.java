package top.sunriseydy.syhthems.db.model;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
import java.util.List;

/**
 * @author SunriseYDY
 * @date 2019-05-28 17:11
 */
@Getter
public class CustomDeviceUserDetails extends User {
    private final Product product;

    private final Device device;

    private final List<DataStream> dataStreams;

    public CustomDeviceUserDetails(String username, String password, Collection<? extends GrantedAuthority> authorities, Product product, Device device, List<DataStream> dataStreams) {
        super(username, password, authorities);
        this.product = product;
        this.device = device;
        this.dataStreams = dataStreams;
    }

    public CustomDeviceUserDetails(String username, String password, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities, Product product, Device device, List<DataStream> dataStreams) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
        this.product = product;
        this.device = device;
        this.dataStreams = dataStreams;
    }
}
