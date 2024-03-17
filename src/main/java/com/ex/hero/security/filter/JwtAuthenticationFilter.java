package com.ex.hero.security.filter;

import static com.ex.hero.security.HeroStatic.*;

import com.ex.hero.member.model.dto.request.SignInRequest;
import com.ex.hero.security.jwt.TokenProvider;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final TokenProvider tokenProvider;


    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            SignInRequest signInRequest = getSignInRequest(request.getInputStream());

            UsernamePasswordAuthenticationToken authenticationToken
                    = createUsernamePasswordAuthenticationToken(signInRequest);

            return authenticationManager.authenticate(authenticationToken);
        } catch (IOException e) {
            e.printStackTrace();
            return super.attemptAuthentication(request, response);
        }
    }

    private SignInRequest getSignInRequest(ServletInputStream inputStream) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(inputStream, SignInRequest.class);
    }

    private UsernamePasswordAuthenticationToken createUsernamePasswordAuthenticationToken(SignInRequest signInRequest) {
        return new UsernamePasswordAuthenticationToken(signInRequest.getEmail(), signInRequest.getPassword());
    }

    @Override
    public void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) {

        String grantedAuthority = authResult.getAuthorities().stream().findAny().orElseThrow().toString();
        String accessToken = tokenProvider.generateAccessToken(authResult.getPrincipal().toString(), grantedAuthority);

        response.addHeader(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, HttpHeaders.AUTHORIZATION);
        response.addHeader(AUTH_HEADER, ACCESS_TOKEN + accessToken);

    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        super.unsuccessfulAuthentication(request, response, failed);
    }

}
