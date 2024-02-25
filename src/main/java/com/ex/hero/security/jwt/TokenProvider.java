package com.ex.hero.security.jwt;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
// import lombok.RequiredArgsConstructor;

@PropertySource("classpath:auth-jwt.yml")
@Service
// @RequiredArgsConstructor
public class TokenProvider {
	// @Value("${auth.jwt.secret-key}")
	private final String secretKey;
	// @Value("${auth.jwt.expiration-hours")
	private final long expirationHours;
	// @Value("${auth.jwt.issuer")
	private final String issuer;

	public TokenProvider(
		@Value("${secret-key}") String secretKey,
		@Value("${expiration-hours}") long expirationHours,
		@Value("${issuer}") String issuer
	){
		this.secretKey = secretKey;
		this.expirationHours = expirationHours;
		this.issuer = issuer;
	}


	public String createToken(String userSpecification) {
		 return Jwts.builder()
			 .signWith(new SecretKeySpec(secretKey.getBytes(), SignatureAlgorithm.HS512.getJcaName()))
			 .setSubject(userSpecification) // Jwt 토큰 제목
			 .setIssuer(issuer)		// Jwt 토큰 발급자
			 .setIssuedAt(Timestamp.valueOf(LocalDateTime.now()))	// Jwt 토큰 발급 시간
			 .setExpiration(Date.from(Instant.now().plus(expirationHours, ChronoUnit.HOURS)))	// Jwt 토큰 만료 시간
			 .compact();	// Jwt 토큰 생성
	}

	public String validateTokenAndGetSubject(String token) {
		return Jwts.parserBuilder()
			.setSigningKey(secretKey.getBytes())
			.build()
			.parseClaimsJws(token)
			.getBody()
			.getSubject();
	}
}
