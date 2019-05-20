package top.sunriseydy.syhthems.web.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

/**
 * @author SunriseYDY
 * @date 2019-05-16 13:44
 */
public class CustomJwtAuthenticationConverter implements Converter<Jwt, AbstractAuthenticationToken> {
    private static final String SCOPE_AUTHORITY_PREFIX = "SCOPE_";

    private static final Collection<String> WELL_KNOWN_SCOPE_ATTRIBUTE_NAMES =
            Arrays.asList("scope", "scp");

    private static final String WELL_KNOWN_AUTHORITIES_ATTRIBUTE_NAME = "authorities";

    @Override
    public final AbstractAuthenticationToken convert(Jwt jwt) {
        Collection<GrantedAuthority> authorities = extractAuthorities(jwt);
        return new JwtAuthenticationToken(jwt, authorities);
    }

    private Collection<GrantedAuthority> extractAuthorities(Jwt jwt) {
        Collection<String> authorities = this.getAuthorities(jwt);
        return authorities
                .stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    @SuppressWarnings("Duplicates")
    private Collection<String> getAuthorities(Jwt jwt) {
        Object authorities = jwt.getClaims().get(WELL_KNOWN_AUTHORITIES_ATTRIBUTE_NAME);
        if (authorities instanceof String) {
            if (StringUtils.hasText((String) authorities)) {
                return Arrays.asList(((String) authorities).split(" "));
            } else {
                return Collections.emptyList();
            }
        } else if (authorities instanceof Collection) {
            return (Collection<String>) authorities;
        }
        return Collections.emptyList();
    }

    @SuppressWarnings("Duplicates")
    private Collection<String> getScopes(Jwt jwt) {
        for ( String attributeName : WELL_KNOWN_SCOPE_ATTRIBUTE_NAMES ) {
            Object scopes = jwt.getClaims().get(attributeName);
            if (scopes instanceof String) {
                if (StringUtils.hasText((String) scopes)) {
                    return Arrays.asList(((String) scopes).split(" "));
                } else {
                    return Collections.emptyList();
                }
            } else if (scopes instanceof Collection) {
                return (Collection<String>) scopes;
            }
        }

        return Collections.emptyList();
    }
}
