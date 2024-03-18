package com.ex.hero.security.filter;

import static com.ex.hero.security.HeroStatic.*;

import com.ex.hero.security.principal.AuthDetails;
import com.ex.hero.security.jwt.AccessTokenInfo;
import com.ex.hero.security.jwt.TokenProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
//@Component
public class JwtTokenFilter extends OncePerRequestFilter {

    private final TokenProvider tokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = resolveToken(request, HttpHeaders.AUTHORIZATION);

        if (token != null) {
            Authentication authentication = getAuthentication(token);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request, response);
    }

    private String resolveToken(HttpServletRequest request, String headerName) {
        String rawHeader = request.getHeader(headerName);
        log.info("{}", rawHeader);

//        if (rawHeader != null
//                && rawHeader.length() > BEARER.length()
//                && rawHeader.startsWith(BEARER)) {
//            return rawHeader.substring(BEARER.length());
//        }
//        return null;

        return Optional.ofNullable(request.getHeader(headerName))
                .filter(token -> token.substring(0, 7).equalsIgnoreCase("Bearer "))
                .map(token -> token.substring(7))
                .orElse(null);
    }

    public Authentication getAuthentication(String token) {
        AccessTokenInfo accessTokenInfo = tokenProvider.parseAccessToken(token);

        UserDetails userDetails = new AuthDetails(accessTokenInfo.getEmail(), accessTokenInfo.getRole());
        return new UsernamePasswordAuthenticationToken(userDetails, "user", userDetails.getAuthorities());
    }


}
