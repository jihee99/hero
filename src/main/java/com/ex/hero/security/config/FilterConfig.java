package com.ex.hero.security.config;

import com.ex.hero.security.filter.JwtTokenFilter;
import com.ex.hero.security.filter.NewJwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.stereotype.Component;


//@RequiredArgsConstructor
//@Component
//public class FilterConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {
//
//    private final NewJwtAuthenticationFilter jwtAuthenticationFilter;
////    private final AccessDeniedFilter accessDeniedFilter;
//
//    @Override
//    public void configure(HttpSecurity builder) {
//        builder.addFilterBefore(jwtTokenFilter, BasicAuthenticationFilter.class);
////        builder.addFilterBefore(accessDeniedFilter, FilterSecurityInterceptor.class);
//    }
//}