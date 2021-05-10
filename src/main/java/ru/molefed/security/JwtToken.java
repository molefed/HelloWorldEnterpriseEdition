package ru.molefed.security;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.io.Serializable;
import java.util.Collection;

/**
 * Токен, который выставляется в SecurityContextHolder Сервера авторизации, после авторизации пользователя
 */
public class JwtToken extends AbstractAuthenticationToken implements Serializable {
    private static final long serialVersionUID = -6321902057211894350L;

    public JwtToken(JwtUserDetail jwtUserDetail, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.setAuthenticated(true);
        this.setDetails(jwtUserDetail);
    }

    @Override
    public String getPrincipal() {
        return ((JwtUserDetail) this.getDetails()).getName();
    }

    @Override
    public Object getCredentials() {
        return null;
    }

}
