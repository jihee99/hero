package com.ex.hero.security.config;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.ex.hero.security.filter.JwtAuthenticationFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {
	private final String[] allowedUrls = {"/", "/swagger-ui/**", "/v3/**", "/sign-up", "/sign-in"};
	private final AuthenticationEntryPoint entryPoint;
	private final JwtAuthenticationFilter jwtAuthenticationFilter;	// JwtAuthenticationFilter 주입

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public WebSecurityCustomizer webSecurityCustomizer() {
		/*
		*  HTML, CSS, JavaScript, image 등 정적 자원에 대해 보안을 적용하지 않도록 설정
		* */
		return web -> web.ignoring()
			.requestMatchers(PathRequest.toStaticResources().atCommonLocations());
	}


	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
			.csrf(AbstractHttpConfigurer::disable)
			.sessionManagement( sessionManageMent -> sessionManageMent.sessionCreationPolicy(
					SessionCreationPolicy.STATELESS))
			.authorizeHttpRequests(requests ->
				requests
					.requestMatchers(allowedUrls).permitAll()

					.requestMatchers("/api/v[0-9]+/member/**").hasAnyAuthority("MEMBER", "HOST", "MANAGER", "ADMIN") // member, seller, admin 권한 허용
					.requestMatchers("/api/v[0-9]+/host/admin/**").hasAnyAuthority("HOST", "ADMIN") // seller, admin 권한 허용
					.requestMatchers("/api/v[0-9]+/host/**").hasAnyAuthority("HOST", "MANAGER", "ADMIN") // seller, admin 권한 허용
					.requestMatchers("/api/v[0-9]+/system/**").hasAuthority("ADMIN") // admin 권한 허용

					.anyRequest().authenticated() // 그 외의 모든 요청은 인증 필요
			)

			// .authorizeHttpRequests(requests ->
			// 	requests.requestMatchers(allowedUrls).permitAll()	// requestMatchers의 인자로 전달된 url은 모두에게 허용
			//
			//
			//
			// 		.anyRequest().authenticated()	// 그 외의 모든 요청은 인증 필요
			// )

			.addFilterBefore(jwtAuthenticationFilter, BasicAuthenticationFilter.class)
			.exceptionHandling(handler -> handler.authenticationEntryPoint(entryPoint))	// 추가
		;
		return http.build();
	}

	@Bean
	public RoleHierarchy roleHierarchy() {
		RoleHierarchyImpl hierarchy = new RoleHierarchyImpl();
		hierarchy.setHierarchy("ROLE_ADMIN > ROLE_HOST > ROLE_MANAGER > ROLE_USER");
		return hierarchy;
	}

	@Bean
	static MethodSecurityExpressionHandler methodSecurityExpressionHandler(RoleHierarchy roleHierarchy) {
		DefaultMethodSecurityExpressionHandler expressionHandler = new DefaultMethodSecurityExpressionHandler();
		expressionHandler.setRoleHierarchy(roleHierarchy);
		return expressionHandler;
	}


}
