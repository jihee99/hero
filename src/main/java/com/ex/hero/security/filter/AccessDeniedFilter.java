//package com.ex.hero.security.filter;
//
//import com.ex.hero.common.dto.ErrorReason;
//import com.ex.hero.common.dto.ErrorResponse;
//import com.ex.hero.common.exception.BaseErrorCode;
//import com.ex.hero.common.exception.HeroException;
//import com.ex.hero.member.exception.MemberErrorCode;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.MediaType;
//import org.springframework.security.access.AccessDeniedException;
//import org.springframework.security.authentication.AuthenticationTrustResolver;
//import org.springframework.security.authentication.AuthenticationTrustResolverImpl;
//import org.springframework.stereotype.Component;
//import org.springframework.util.PatternMatchUtils;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import java.io.IOException;
//
//@RequiredArgsConstructor
//@Component
//public class AccessDeniedFilter extends OncePerRequestFilter {
//    public static final String[] SwaggerPatterns = {"/swagger-resources/**", "/swagger-ui/**",};
//    private final ObjectMapper objectMapper;
//
//    private AuthenticationTrustResolver authenticationTrustResolver = new AuthenticationTrustResolverImpl();
//
//    @Override
//    protected boolean shouldNotFilter(HttpServletRequest request) {
//        String servletPath = request.getServletPath();
//        return PatternMatchUtils.simpleMatch(SwaggerPatterns, servletPath);
//    }
//
//    @Override
//    protected void doFilterInternal( HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//        try {
//            filterChain.doFilter(request, response);
//        } catch (HeroException e) {
//            responseToClient(
//                    response,
//                    getErrorResponse(e.getErrorCode(), request.getRequestURL().toString()));
//        } catch (AccessDeniedException e) {
//            ErrorResponse access_denied = new ErrorResponse(MemberErrorCode.ACCESS_TOKEN_NOT_EXIST.getErrorReason(), request.getRequestURL().toString());
//            responseToClient(response, access_denied);
//        }
//    }
//
//    private ErrorResponse getErrorResponse(BaseErrorCode errorCode, String path) {
//        ErrorReason errorReason = errorCode.getErrorReason();
//        return new ErrorResponse(
//                errorReason.getHttpStatus().value(), errorReason.getErrorCode(), errorReason.getErrorMessage(), path);
//    }
//
//    private void responseToClient(HttpServletResponse response, ErrorResponse errorResponse)
//            throws IOException {
//        response.setCharacterEncoding("UTF-8");
//        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
//        response.setStatus(errorResponse.getStatus());
//        response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
//    }
//}
