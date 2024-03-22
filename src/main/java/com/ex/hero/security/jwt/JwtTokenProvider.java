package com.ex.hero.security.jwt;

import static com.ex.hero.common.HeroStatic.*;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.ex.hero.common.dto.AccessTokenInfo;
import com.ex.hero.common.exception.ExpiredTokenException;
import com.ex.hero.common.exception.RefreshTokenExpiredException;
import com.ex.hero.user.exception.InvalidTokenException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtTokenProvider {

	// private final RedisTemplate<String, String> redisTemplate;

	private final JwtProperties jwtProperties;


	private Key getSecretKey() {
		return Keys.hmacShaKeyFor(jwtProperties.getSecretKey().getBytes(StandardCharsets.UTF_8));
	}

	private Jws<Claims> getJws(String token) {
		try {
			return Jwts.parserBuilder().setSigningKey(getSecretKey()).build().parseClaimsJws(token);
		} catch (ExpiredJwtException e) {
			throw new RuntimeException("만료된 토큰");
//            throw ExpiredTokenException.EXCEPTION;
		} catch (Exception e) {
			throw new RuntimeException("유효하지 않은 토큰");
			//            throw InvalidTokenException.EXCEPTION;
		}
	}
	/* Access 토큰 생성 */
	public String generateAccessToken(Long id, String role){

		final Date issuedAt = new Date();
		final Date accessTokenExpiresIn = new Date(issuedAt.getTime() + jwtProperties.getAccessExp());
		final Key encodedKey = getSecretKey();

		return Jwts.builder()
			.setIssuedAt(issuedAt)
			.setSubject(String.valueOf(id))
			.claim(TOKEN_TYPE, ACCESS_TOKEN)
			.claim(TOKEN_ROLE, role)
			.setExpiration(accessTokenExpiresIn)
			.signWith(encodedKey)
			.compact();
	}

	/* Refresh 토큰 생성 */
	public String generateRefreshToken(Long id){

		final Date issuedAt = new Date();
		final Date refreshTokenExpiresIn = new Date(issuedAt.getTime() + jwtProperties.getRefreshExp());
		final Key encodedKey = getSecretKey();

		String refreshToken = Jwts.builder()
			.setSubject(String.valueOf(id))
			.claim(TOKEN_TYPE, REFRESH_TOKEN)
			.setIssuedAt(issuedAt)
			.setExpiration(refreshTokenExpiresIn)
			.signWith(encodedKey)
			.compact();

		// redis에 저장
		// redisTemplate.opsForValue().set(
		// 	email,
		// 	refreshToken,
		// 	jwtProperties.getRefreshExp(),
		// 	TimeUnit.MILLISECONDS
		// );

		return refreshToken;
	}

	public boolean isAccessToken(String token) {
		return getJws(token).getBody().get(TOKEN_TYPE).equals(ACCESS_TOKEN);
	}

	public boolean isRefreshToken(String token) {
		return getJws(token).getBody().get(TOKEN_TYPE).equals(REFRESH_TOKEN);
	}

	public AccessTokenInfo parseAccessToken(String token) {
		if (isAccessToken(token)) {
			Claims claims = getJws(token).getBody();
			return AccessTokenInfo.builder()
				.userId(Long.parseLong(claims.getSubject()))
//				.email(claims.getSubject())
				.role((String) claims.get(TOKEN_ROLE))
				.build();
		}
		throw InvalidTokenException.EXCEPTION;
	}

	public Long parseRefreshToken(String token) {
		try {
			if (isRefreshToken(token)) {
				Claims claims = getJws(token).getBody();
				return Long.parseLong(claims.getSubject());
			}
		} catch (ExpiredTokenException e) {
			throw RefreshTokenExpiredException.EXCEPTION;
		}
		throw InvalidTokenException.EXCEPTION;
	}

	public Long getRefreshTokenTTlSecond() {
		return jwtProperties.getRefreshExp();
	}

	public Long getAccessTokenTTlSecond() {
		return jwtProperties.getAccessExp();
	}


}
