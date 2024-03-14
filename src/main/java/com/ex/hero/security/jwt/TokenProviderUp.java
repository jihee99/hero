package com.ex.hero.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.Duration;
import java.util.Date;

@RequiredArgsConstructor
@Component
public class TokenProviderUp {

    private final JwtProperties jwtProperties;
    private final String TOKEN_TYPE = "type";
    private final String TOKEN_ROLE = "role";
    private static final Duration ACCESS_TOKEN_EXPIRATION_TIME = Duration.ofMinutes(30);
    private static final Duration REFRESH_TOKEN_EXPIRATION_TIME = Duration.ofMinutes(60);

    private Jws<Claims> getJws(String token) {
        try {
            return Jwts.parserBuilder().setSigningKey(getSecretKey()).build().parseClaimsJws(token);
        } catch (ExpiredJwtException e) {
            throw new RuntimeException("만료된 토큰");
//            TODO 예외처리
//            throw ExpiredTokenException.EXCEPTION;
        } catch (Exception e) {
            throw new RuntimeException("유효하지 않은 토큰");
//            TODO 예외처리
//            throw InvalidTokenException.EXCEPTION;
        }
    }

    private Key getSecretKey() {
        return Keys.hmacShaKeyFor(jwtProperties.getSecretKey().getBytes(StandardCharsets.UTF_8));
    }

    public String generateAccessToken(Long id, String role) {
        final Date issuedAt = new Date();
        final Date accessTokenExpiresIn =  new Date(issuedAt.getTime() + ACCESS_TOKEN_EXPIRATION_TIME.toMillis());

        final Key encodedKey = getSecretKey();
        return Jwts.builder()
//                .setIssuer(TOKEN_ISSUER)
                .setIssuedAt(issuedAt)
                .setSubject(id.toString())
                .claim(TOKEN_TYPE, "ACCESS_TOKEN")
                .claim(TOKEN_ROLE, role)
                .setExpiration(accessTokenExpiresIn)
                .signWith(encodedKey)
                .compact();

    }

    public String generateRefreshToken(Long id) {
        final Date issuedAt = new Date();
        final Date refreshTokenExpiresIn = new Date(issuedAt.getTime() + REFRESH_TOKEN_EXPIRATION_TIME.toMillis());

        final Key encodedKey = getSecretKey();
        return Jwts.builder()
//                .setIssuer(TOKEN_ISSUER)
                .setIssuedAt(issuedAt)
                .setSubject(id.toString())
                .claim(TOKEN_TYPE, "REFRESH_TOKEN")
                .setExpiration(refreshTokenExpiresIn)
                .signWith(encodedKey)
                .compact();
    }

    public boolean isAccessToken(String token) {
        return getJws(token).getBody().get(TOKEN_TYPE).equals("ACCESS_TOKEN");
    }

    public boolean isRefreshToken(String token) {
        return getJws(token).getBody().get(TOKEN_TYPE).equals("REFRESH_TOKEN");
    }

    public AccessTokenInfo parseAccessToken(String token) {
        if (isAccessToken(token)) {
            Claims claims = getJws(token).getBody();
            return AccessTokenInfo.builder()
//                    .userId(Long.parseLong(claims.getSubject()))
                    .email(claims.getSubject())
                    .role((String) claims.get(TOKEN_ROLE))
                    .build();
        }
        throw new RuntimeException();
//        throw InvalidTokenException.EXCEPTION;
    }

    public Long parseRefreshToken(String token) {
        try {
            if (isRefreshToken(token)) {
                Claims claims = getJws(token).getBody();
                return Long.parseLong(claims.getSubject());
            }
        } catch (Exception e) {
            throw new RuntimeException("유효하지 않은 토큰");
        }
        throw new RuntimeException("유효하지 않은 토큰");
//        catch (ExpiredTokenException e) {
//            throw RefreshTokenExpiredException.EXCEPTION;
//        }
//        throw InvalidTokenException.EXCEPTION;
    }

    public Long getRefreshTokenTTlSecond() {
        return jwtProperties.getRefreshExp();
    }

    public Long getAccessTokenTTlSecond() {
        return jwtProperties.getAccessExp();
    }

}
