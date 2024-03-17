// package com.ex.hero.security.filter;
//
// import static com.ex.hero.security.HeroStatic.*;
//
// import com.ex.hero.member.model.Member;
// import com.ex.hero.member.repository.MemberRepository;
// import com.ex.hero.security.jwt.TokenProvider;
// import com.ex.hero.security.principal.PrincipalDetails;
// import jakarta.servlet.FilterChain;
// import jakarta.servlet.ServletException;
// import jakarta.servlet.http.HttpServletRequest;
// import jakarta.servlet.http.HttpServletResponse;
// import org.springframework.security.authentication.AuthenticationManager;
// import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
// import org.springframework.security.core.Authentication;
// import org.springframework.security.core.context.SecurityContextHolder;
// import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
//
// import java.io.IOException;
//
// public class JwtAuthorizationFilter extends BasicAuthenticationFilter {
//
//     private final TokenProvider tokenProvider;
//     private final MemberRepository memberRepository;
//
//     public JwtAuthorizationFilter(
//         AuthenticationManager authenticationManager,
//         TokenProvider tokenProvider,
//         MemberRepository memberRepository
//     ) {
//         super(authenticationManager);
//         this.tokenProvider = tokenProvider;
//         this.memberRepository = memberRepository;
//     }
//
//     @Override
//     protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
//         throws ServletException, IOException {
//         if (isNullToken(request)) {
//             chain.doFilter(request, response);
//             return;
//         }
//
//         String jwtToken = request.getHeader(AUTH_HEADER)
//             .replace(ACCESS_TOKEN, "");
//
//         if (tokenProvider.validateTokenExpirationTimeNotExpired(jwtToken)) {
//
//             String email = tokenProvider.getEmail(jwtToken);
//             Member member = memberRepository.findByEmail(email).orElseThrow();
//             PrincipalDetails principalDetails = new PrincipalDetails(member);
//
//             Authentication authentication = new UsernamePasswordAuthenticationToken(principalDetails, "", principalDetails.getAuthorities());
//
//             SecurityContextHolder.getContext().setAuthentication(authentication);
//         }
//
//         chain.doFilter(request, response);
//     }
//
//     private boolean isNullToken(HttpServletRequest request) {
//         String jwtHeader = request.getHeader(AUTH_HEADER);
//         if (jwtHeader == null || !jwtHeader.startsWith(ACCESS_TOKEN)) {
//             return true;
//         }
//         return false;
//     }
// }
