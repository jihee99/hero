package com.ex.hero.security.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.ex.hero.security.handler.JwtAccessDeniedHandler;
import com.ex.hero.security.handler.JwtAuthenticationEntryPoint;
import com.ex.hero.security.jwt.JwtTokenFilter;
import com.ex.hero.security.jwt.JwtTokenProvider;

@Configuration
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final String[] allowedUrls = {"/swagger-ui/**", "/v3/**", "/sign-up", "/sign-in", "/login", "/error"};

	private final JwtTokenProvider jwtTokenProvider;
	private final JwtTokenFilter jwtTokenFilter;
	private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
	private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web -> web.ignoring()
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(CsrfConfigurer<HttpSecurity>::disable)

            .formLogin((formLogin) -> formLogin.disable())
            .httpBasic((httpBasic) -> httpBasic.disable())

            .headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin))

            .sessionManagement(sessionManagement  -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

            .authorizeHttpRequests(requests -> requests

                    .requestMatchers(allowedUrls).permitAll()
                    .requestMatchers(PathRequest.toH2Console()).permitAll()

//                    .requestMatchers("/api/v[0-9]+/events/**").permitAll()
//                    .requestMatchers("/api/v[0-9]+/group/**").hasAnyAuthority("MASTER", "MANAGER", "ADMIN")
//
//                    .requestMatchers("/api/v[0-9]+/member/**").hasAnyAuthority("USER", "ADMIN")
//                    .requestMatchers("/api/v[0-9]+/manager/**").hasAnyAuthority("MASTER", "MANAGER", "ADMIN")
//                    .requestMatchers("/api/v[0-9]+/master/**").hasAnyAuthority("MASTER", "ADMIN")
//                    .requestMatchers("/api/v[0-9]+/system/**").hasAuthority("ADMIN") // admin 권한 허용

                    // 인증이 필요한 모든 요청은 최소 USER 권한을 갖고 있어야 한다.
                    .anyRequest().authenticated()
            );

//		http.exceptionHandling(handler -> handler
//			.authenticationEntryPoint(jwtAuthenticationEntryPoint)
//			.accessDeniedHandler(jwtAccessDeniedHandler)
//		);


		http.addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();

	}
}
