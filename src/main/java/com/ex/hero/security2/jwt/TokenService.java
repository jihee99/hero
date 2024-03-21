//package com.ex.hero.security.filter;
//
//import com.ex.hero.security.jwt.AccessTokenInfo;
//import com.ex.hero.security.dto.Token;
//import io.jsonwebtoken.*;
//import io.jsonwebtoken.security.Keys;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.PropertySource;
//import org.springframework.stereotype.Service;
//
//import java.nio.charset.StandardCharsets;
//import java.security.Key;
//import java.sql.Timestamp;
//import java.time.Duration;
//import java.time.LocalDateTime;
//import java.util.Date;
//
//@Slf4j
//@Service
//@RequiredArgsConstructor
//@PropertySource("classpath:auth-jwt.yml")
//public class TokenService {
//
//    @Value("${secret-key}")
//    private final String SECRET_KEY;
//    private static final Duration ACCESS_TOKEN_EXPIRATION_TIME = Duration.ofMinutes(30);
//    private static final Duration REFRESH_TOKEN_EXPIRATION_TIME = Duration.ofMinutes(60);
//
//    private Jws<Claims> getJws(String token) {
//        try {
//            return Jwts.parserBuilder().setSigningKey(getSecretKey()).build().parseClaimsJws(token);
//        } catch (ExpiredJwtException e) {
//            throw new RuntimeException("만료된 토큰");
////            TODO 예외처리
////            throw ExpiredTokenException.EXCEPTION;
//        } catch (Exception e) {
//            throw new RuntimeException("유효하지 않은 토큰");
////            TODO 예외처리
////            throw InvalidTokenException.EXCEPTION;
//        }
//    }
//
//    public Token createToken(String email, String roleKey) {
//        Claims claims = Jwts.claims().setSubject(email);
//        claims.put("role", roleKey);
//        String accessToken = createAccessToken(email, roleKey);
//        String refreshToken = createRefreshToken(email, roleKey);
//        return new Token(accessToken, refreshToken);
//    }
//
//    public String createAccessToken(String email, String role){
//        final Date issuedAt = new Date();
//        final Date accessTokenExpiresIn =  new Date(issuedAt.getTime() + ACCESS_TOKEN_EXPIRATION_TIME.toMillis());
//
//        final Key encodedKey = getSecretKey();
//
//        return Jwts.builder()
//                .setIssuedAt(Timestamp.valueOf(LocalDateTime.now()))
//                .setSubject(email)
//                .claim("type", "ACCESS_TOKEN")
//                .claim("role", role)
//                .setExpiration(accessTokenExpiresIn)
//                .signWith(encodedKey)
//                .compact();
//    }
//
//    public String createRefreshToken(String email, String role){
//        final Date issuedAt = new Date();
//        final Date refreshTokenExpiresIn = new Date(issuedAt.getTime() + REFRESH_TOKEN_EXPIRATION_TIME.toMillis());
//
//        final Key encodedKey = getSecretKey();
//
//        return Jwts.builder()
//                .setIssuedAt(Timestamp.valueOf(LocalDateTime.now()))
//                .setSubject(email)
//                .claim("role", role)
//                .claim("type", "REFRESH_TOKEN")
//                .setExpiration(refreshTokenExpiresIn)
//                .signWith(encodedKey)
//                .compact();
//    }
//
//    private Key getSecretKey() {
//        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));
//    }
//
//    public boolean isAccessToken(String token) {
//        return getJws(token).getBody().get("type").equals("ACCESS_TOKEN");
//    }
//
//     public boolean isRefreshToken(String token){
//         return getJws(token).getBody().get("type").equals("REFRESH_TOKEN");
//     }
//
//    public AccessTokenInfo parseAccessToken(String token) {
//        if (isAccessToken(token)) {
//            Claims claims = getJws(token).getBody();
//            return AccessTokenInfo.builder()
//                    .email(claims.getSubject())
//                    .role((String) claims.get("role"))
//                    .build();
//        }
//        throw new RuntimeException("토큰유효하지않음");
////        throw InvalidTokenException.EXCEPTION;
//    }
//
//    public String parseRefreshToken(String token) {
//        try {
//            if (isRefreshToken(token)) {
//                Claims claims = getJws(token).getBody();
//                return claims.getSubject();
//            }
//        } catch (Exception e) {
//            throw new RuntimeException("토큰 에러");
//        }
//        throw new RuntimeException("토큰 에러");
////        } catch (ExpiredTokenException e) {
////            throw RefreshTokenExpiredException.EXCEPTION;
////        }
////        throw InvalidTokenException.EXCEPTION;
//    }
//
//    public Long getRefreshTokenTTlSecond() {
//        return ACCESS_TOKEN_EXPIRATION_TIME.toMillis();
//    }
//
//    public Long getAccessTokenTTlSecond() {
//        return REFRESH_TOKEN_EXPIRATION_TIME.toMillis();
//    }
//
//}
