package ru.molefed.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.molefed.db.entity.user.AppRole;
import ru.molefed.db.entity.user.AppUser;
import ru.molefed.service.RefreshTokenService;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class JwtProvider {

    @Value("$(jwt.secret)")
    private String jwtSecret;
    @Value("$(jwt.expiresInMin)")
    private long expiresInMin;
    @Value("$(jwt.refreshExpiresInMin)")
    private long refreshExpiresInMin;
    private final RefreshTokenService refreshTokenService;

    @Autowired
    public JwtProvider(RefreshTokenService refreshTokenService) {
        this.refreshTokenService = refreshTokenService;
    }

    @SuppressWarnings("UseOfObsoleteDateTimeApi")
    private Date calcExpiresDate(long expiresMin) {
        return Date.from(LocalDateTime.now()
                .plusMinutes(expiresMin)
                .atZone(ZoneId.systemDefault()).toInstant());
    }

    public TokenInfo generateToken(AppUser appUser) {
        String compact = Jwts.builder()
                .setSubject(appUser.getName())
                .claim("id", appUser.getId())
                .claim("roles", appUser.getRoles().stream()
                        .map(AppRole::getName)
                        .collect(Collectors.joining(" ")))
                .setExpiration(calcExpiresDate(expiresInMin))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
        return new TokenInfo(compact, expiresInMin * 60);
    }

    @Transactional
    public TokenInfo generateRefreshToken(AppUser appUser) {
        String compact = Jwts.builder()
                .setExpiration(calcExpiresDate(refreshExpiresInMin))
                .signWith(SignatureAlgorithm.HS256, jwtSecret)
                .compact();

        refreshTokenService.saveNew(compact, appUser, LocalDateTime.now().plusMinutes(refreshExpiresInMin));

        return new TokenInfo(compact, refreshExpiresInMin * 60);
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
            return true;
        } catch (Throwable ignore) {
        }
        return false;
    }

    public JwtToken craeteJwtToken(String token) {
        Claims claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();

        String rolesStr = (String) claims.get("roles");
        List<GrantedAuthority> grantList = Arrays.stream(rolesStr.split(" "))
                .map(role -> "ROLE_" + role)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        JwtUserDetail detail = new JwtUserDetail();
        detail.setId(((Number) claims.get("id")).longValue());
        detail.setName(claims.getSubject());

        return new JwtToken(detail, grantList);
    }

    public static class TokenInfo {
        private final String token;
        private final long expiresInSec;

        public TokenInfo(String token, long expiresInSec) {
            this.token = token;
            this.expiresInSec = expiresInSec;
        }

        public String getToken() {
            return token;
        }

        public long getExpiresInSec() {
            return expiresInSec;
        }

    }

}
