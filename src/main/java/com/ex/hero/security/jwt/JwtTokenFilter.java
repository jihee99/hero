package com.ex.hero.security.jwt;

import static com.ex.hero.common.HeroStatic.*;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.WebUtils;

import com.ex.hero.common.dto.AccessTokenInfo;
import com.ex.hero.security.AuthDetails;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class JwtTokenFilter extends OncePerRequestFilter {

	private final JwtTokenProvider jwtTokenProvider;

	@Override
	protected void doFilterInternal(
		HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
		throws ServletException, IOException {
		String token = resolveToken(request);

		if (token != null) {
			Authentication authentication = getAuthentication(token);
			SecurityContextHolder.getContext().setAuthentication(authentication);
		}

		filterChain.doFilter(request, response);
	}

	private String resolveToken(HttpServletRequest request) {
		// 기존 jwt 방식 지원
		String rawHeader = request.getHeader(AUTH_HEADER);

		if (rawHeader != null && rawHeader.length() > BEARER.length() && rawHeader.startsWith(BEARER)) {
			return rawHeader.substring(BEARER.length());
		}
		return null;
	}

	public Authentication getAuthentication(String token) {
		AccessTokenInfo accessTokenInfo = jwtTokenProvider.parseAccessToken(token);

		UserDetails userDetails = new AuthDetails(accessTokenInfo.getUserId(), accessTokenInfo.getRole());
		return new UsernamePasswordAuthenticationToken(userDetails, "user", userDetails.getAuthorities());
	}

}
