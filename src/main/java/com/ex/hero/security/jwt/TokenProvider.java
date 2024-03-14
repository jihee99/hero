package com.ex.hero.security.jwt;

import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ex.hero.member.repository.MemberRefreshTokenRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@PropertySource("classpath:auth-jwt.yml")
@Service
// @RequiredArgsConstructor
public class TokenProvider {

	private final String secretKey;
	private final long expirationMinutes;
	private final long refreshExpirationHours;
	private final String issuer;
	private final long reissueLimit;
	private final MemberRefreshTokenRepository memberRefreshTokenRepository;
	public static final int MILLI_TO_SECOND = 1000;

	private final ObjectMapper objectMapper = new ObjectMapper();	// JWT 역직렬화를 위한 ObjectMapper

	public TokenProvider(
		@Value("${secret-key}") String secretKey,
		@Value("${expiration-minutes}") long expirationMinutes,
		@Value("${refresh-expiration-hours}") long refreshExpirationHours,
		@Value("${issuer}") String issuer,
		MemberRefreshTokenRepository memberRefreshTokenRepository
	) {
		this.secretKey = secretKey;
		this.expirationMinutes = expirationMinutes;
		this.refreshExpirationHours = refreshExpirationHours;
		this.issuer = issuer;
		this.memberRefreshTokenRepository = memberRefreshTokenRepository;	// 추가
		reissueLimit = refreshExpirationHours * 60 / expirationMinutes;	// 재발급 한도
	}




	public String createAccessToken(String userSpecification) {

		final Date issuedAt = new Date();
		final Date accessTokenExpiresIn = new Date(issuedAt.getTime() + 360 * MILLI_TO_SECOND);

		 return Jwts.builder()
			 .signWith(new SecretKeySpec(secretKey.getBytes(), SignatureAlgorithm.HS512.getJcaName()))
			 .setSubject(userSpecification) // Jwt 토큰 제목
			 .setIssuer(issuer)		// Jwt 토큰 발급자
			 .setIssuedAt(Timestamp.valueOf(LocalDateTime.now()))	// Jwt 토큰 발급 시간
			 .setExpiration(accessTokenExpiresIn)	// Jwt 토큰 만료 시간
			 .compact();	// Jwt 토큰 생성
	}

	public String createRefreshToken() {
		return Jwts.builder()
			.signWith(new SecretKeySpec(secretKey.getBytes(), SignatureAlgorithm.HS512.getJcaName()))
			.setIssuer(issuer)
			.setIssuedAt(Timestamp.valueOf(LocalDateTime.now()))
			.setExpiration(Date.from(Instant.now().plus(refreshExpirationHours, ChronoUnit.HOURS)))
			.compact();
	}

	public String validateTokenAndGetSubject(String token) {
		return Jwts.parserBuilder()
			.setSigningKey(secretKey.getBytes())
			.build()
			.parseClaimsJws(token)
			.getBody()
			.getSubject();
	}

	@Transactional
	public String recreateAccessToken(String oldAccessToken) throws JsonProcessingException {
		String subject = decodeJwtPayloadSubject(oldAccessToken);
		memberRefreshTokenRepository.findByMemberIdAndReissueCountLessThan(Long.parseLong(subject.split(":")[0]), reissueLimit)
			.ifPresentOrElse(
				MemberRefreshToken::increaseReissueCount,
				() -> { throw new ExpiredJwtException(null, null, "Refresh token expired."); }
			);
		return createAccessToken(subject);
	}

	@Transactional(readOnly = true)
	public void validateRefreshToken(String refreshToken, String oldAccessToken) throws JsonProcessingException {
		validateAndParseToken(refreshToken);
		String memberId = decodeJwtPayloadSubject(oldAccessToken).split(":")[0];
		memberRefreshTokenRepository.findByMemberIdAndReissueCountLessThan(Long.parseLong(memberId), reissueLimit)
			.filter(memberRefreshToken -> memberRefreshToken.validateRefreshToken(refreshToken))
			.orElseThrow(() -> new ExpiredJwtException(null, null, "Refresh token expired."));
	}

	private Jws<Claims> validateAndParseToken(String token) {	// validateTokenAndGetSubject에서 따로 분리
		return Jwts.parserBuilder()
			.setSigningKey(secretKey.getBytes())
			.build()
			.parseClaimsJws(token);
	}

	private String decodeJwtPayloadSubject(String oldAccessToken) throws JsonProcessingException {
		return objectMapper.readValue(
			new String(Base64.getDecoder().decode(oldAccessToken.split("\\.")[1]), StandardCharsets.UTF_8),
			Map.class
		).get("sub").toString();
	}

}
