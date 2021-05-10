package ru.molefed.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import ru.molefed.db.entity.user.AppRole;
import ru.molefed.db.entity.user.AppUser;

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

    public String generateToken(AppUser appUser) {
        @SuppressWarnings("UseOfObsoleteDateTimeApi")
        Date date = Date.from(LocalDateTime.now().plusMinutes(30).atZone(ZoneId.systemDefault()).toInstant());
        return Jwts.builder()
                .setSubject(appUser.getName())
                .claim("id", appUser.getId())
                .claim("roles", appUser.getRoles().stream()
                        .map(AppRole::getName)
                        .collect(Collectors.joining(";")))
                .setExpiration(date)
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
            return true;
        } catch (Throwable ignore) {
        }
        return false;
    }

    public JwtToken craeteToken(String token) {
        Claims claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();

        String rolesStr = (String) claims.get("roles");
        List<GrantedAuthority> grantList = Arrays.stream(rolesStr.split(";"))
                .map(r -> "ROLE_" + r)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        JwtUserDetail detail = new JwtUserDetail();
        detail.setId(((Number) claims.get("id")).longValue());
        detail.setName(claims.getSubject());

        return new JwtToken(detail, grantList);
    }

}
