package ru.molefed.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

import static org.springframework.util.StringUtils.hasText;

@Component
@RequiredArgsConstructor
public class JwtBearerFilter extends GenericFilterBean {

	public static final String AUTHORIZATION = "Authorization";

	private final JwtProvider jwtProvider;

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
		String token = getTokenFromRequest((HttpServletRequest) servletRequest);
		if (token != null && jwtProvider.validateToken(token)) {
			SecurityContextHolder.getContext().setAuthentication(jwtProvider.craeteJwtToken(token));
		}

		filterChain.doFilter(servletRequest, servletResponse);
	}

	private String getTokenFromRequest(HttpServletRequest request) {
		String bearer = request.getHeader(AUTHORIZATION);
		if (hasText(bearer) && bearer.startsWith("Bearer ")) {
			return bearer.substring(7);
		}
		return null;
	}
}
