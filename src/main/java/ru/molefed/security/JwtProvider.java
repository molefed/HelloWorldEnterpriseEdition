package ru.molefed.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.molefed.persister.entity.user.AppRole;
import ru.molefed.persister.entity.user.AppUser;
import ru.molefed.property.JwtProperty;
import ru.molefed.service.RefreshTokenService;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class JwtProvider {

	private static final ZoneId UTC = ZoneId.of("UTC");

	private final RefreshTokenService refreshTokenService;
	private final JwtProperty jwtProperty;

	@SuppressWarnings("UseOfObsoleteDateTimeApi")
	private Date calcExpiresDate(long expiresMin) {
		return Date.from(LocalDateTime.now()
								 .plusMinutes(expiresMin)
								 .atZone(UTC).toInstant());
	}

	public TokenInfo generateToken(AppUser appUser) {
		String compact = Jwts.builder()
				.setSubject(appUser.getName())
				.claim("id", appUser.getId())
				.claim("roles", appUser.getRoles().stream()
						.map(AppRole::getName)
						.collect(Collectors.joining(" ")))
				.setExpiration(calcExpiresDate(jwtProperty.getExpiresInMin()))
				.signWith(SignatureAlgorithm.HS512, jwtProperty.getSecret())
				.compact();
		return new TokenInfo(compact, jwtProperty.getExpiresInMin() * 60);
	}

	@Transactional
	public TokenInfo generateAndSaveRefreshToken(AppUser appUser) {
		String compact = Jwts.builder()
				.setExpiration(calcExpiresDate(jwtProperty.getRefreshExpiresInMin()))
				.signWith(SignatureAlgorithm.HS256, jwtProperty.getSecret())
				.compact();

		refreshTokenService.saveNew(compact, appUser, LocalDateTime.now().plusMinutes(jwtProperty.getRefreshExpiresInMin()));

		return new TokenInfo(compact, jwtProperty.getRefreshExpiresInMin() * 60);
	}

	public boolean validateToken(String token) {
		try {
			Jwts.parser().setSigningKey(jwtProperty.getSecret()).parseClaimsJws(token);
			return true;
		} catch (Throwable ignore) {
		}
		return false;
	}

	public JwtToken craeteJwtToken(String token) {
		Claims claims = Jwts.parser().setSigningKey(jwtProperty.getSecret()).parseClaimsJws(token).getBody();

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
