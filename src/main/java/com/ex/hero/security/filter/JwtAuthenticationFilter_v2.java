//package com.ex.hero.security.filter;
//
//import com.ex.hero.member.model.dto.request.SignInRequest;
//import com.ex.hero.security.jwt.Token;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletInputStream;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.RequiredArgsConstructor;
//import lombok.Setter;
//import org.springframework.http.HttpHeaders;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//
//import java.io.IOException;
//
//@RequiredArgsConstructor
//public class JwtAuthenticationFilter_v2  extends UsernamePasswordAuthenticationFilter {
//
//    private static final String HEADER_STRING = HttpHeaders.AUTHORIZATION;
//    private static final String ACCESS_TOKEN_PREFIX = "Access-Token ";
//    private static final String REFRESH_TOKEN_PREFIX = "Refresh-Token ";
//    private final AuthenticationManager authenticationManager;
//    private final TokenService tokenService;
//
//    @Override
//    public Authentication attemptAuthentication(
//            HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
//        try {
//            SignInRequest signInRequest = getSignInRequest(request.getInputStream());
//            UsernamePasswordAuthenticationToken authenticationToken = createUsernamePasswordAuthenticationToken(signInRequest);
//
//            return authenticationManager.authenticate(authenticationToken);
//        } catch (IOException e) {
//            e.printStackTrace();
//            return super.attemptAuthentication(request, response);
//        }
//    }
//
//    private UsernamePasswordAuthenticationToken createUsernamePasswordAuthenticationToken(SignInRequest signInRequest) {
//        return new UsernamePasswordAuthenticationToken(signInRequest.getEmail(), signInRequest.getPassword());
//    }
//
//    @Override
//    public void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) {
//
//        String grantedAuthority = authResult.getAuthorities().stream().findAny().orElseThrow().toString();
//        Token jwtToken = tokenService.createToken(authResult.getPrincipal().toString(), grantedAuthority);
//
//        response.addHeader(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, HttpHeaders.AUTHORIZATION);
//        response.addHeader(HEADER_STRING, ACCESS_TOKEN_PREFIX + jwtToken.getAccessToken());
//        response.addHeader(HEADER_STRING, REFRESH_TOKEN_PREFIX + jwtToken.getRefreshToken());
//    }
//
//
//
//    @Getter
//    @Setter
//    @NoArgsConstructor
//    private static class SignInRequest {
//        private String email;
//        private String password;
//    }
//
//    private SignInRequest getSignInRequest(ServletInputStream inputStream) throws IOException {
//        ObjectMapper objectMapper = new ObjectMapper();
//        return objectMapper.readValue(inputStream, SignInRequest.class);
//    }
//
//
//}
