//package com.ex.hero.security.filter;
//
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.web.AuthenticationEntryPoint;
//import org.springframework.stereotype.Component;
//import org.springframework.web.servlet.HandlerExceptionResolver;
//
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//
//@Component
//public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
//    private final HandlerExceptionResolver resolver;
//
//    public JwtAuthenticationEntryPoint(@Qualifier("handlerExceptionResolver") HandlerExceptionResolver resolver) {
//        this.resolver = resolver;
//    }
//
//    @Override
//    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) {
//		// JwtAuthenticationFilter에서 request에 담아서 보내준 예외를 처리@Slf4j
//        //@Component
//        //public class JwtAccessDeniedHandler implements AccessDeniedHandler {
//        //
//        //    @Override
//        //    public void handle(HttpServletRequest request,
//        //                       HttpServletResponse response,
//        //                       AccessDeniedException accessDeniedException) throws IOException {
//        //
//        //        response.setCharacterEncoding("utf-8");
//        //        response.sendError(403, "권한이 없습니다.");
//        //    }
//        //}
//		resolver.resolveException(request, response, null, (Exception) request.getAttribute("exception"));
//	}
//}
