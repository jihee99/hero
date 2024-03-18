package com.ex.hero.security.config;

import com.ex.hero.member.repository.MemberRepository;
import com.ex.hero.security.filter.JwtTokenFilter;
import com.ex.hero.security.filter.JwtAuthenticationFilter;
import com.ex.hero.security.jwt.TokenProvider;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import lombok.RequiredArgsConstructor;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {
	private final String[] allowedUrls = {"/swagger-ui/**", "/v3/**", "/sign-up", "/sign-in", "/login", "/new"};
	private final AuthenticationConfiguration authenticationConfiguration;
	private final TokenProvider tokenProvider;

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
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}

	@Bean
	public SecurityFilterChain filterChainForActuator(HttpSecurity http) throws Exception {
		http
			.csrf(AbstractHttpConfigurer::disable)
//			.addFilterBefore(corsFilter, ChannelProcessingFilter.class)

			.formLogin((formLogin) -> formLogin.disable())
			.httpBasic((httpBasic) -> httpBasic.disable())

			.sessionManagement( sessionManageMent -> sessionManageMent.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
			.authorizeHttpRequests(requests ->
				requests
					.requestMatchers(allowedUrls).permitAll()
//						.requestMatchers("/api/v[0-9]+/event")
					.requestMatchers("/api/v[0-9]+/member/**").hasAnyAuthority("USER", "MASTER", "MANAGER", "ADMIN")
					.requestMatchers("/api/v[0-9]+/orders/**").hasAnyAuthority("USER", "MASTER", "MANAGER", "ADMIN")
					.requestMatchers("/api/v[0-9]+/group/**").hasAnyAuthority("MASTER", "MANAGER", "ADMIN") // seller, admin 권한 허용
//					.requestMatchers("/api/v[0-1]+/group").hasAnyAuthority("USER", "MASTER", "MANAGER", "ADMIN")
					.requestMatchers("/api/v[0-9]+/manager/**").hasAnyAuthority("MASTER", "MANAGER", "ADMIN")
					.requestMatchers("/api/v[0-9]+/master/**").hasAnyAuthority("MASTER", "ADMIN") // seller, admin 권한 허용
					.requestMatchers("/api/v[0-9]+/system/**").hasAuthority("ADMIN") // admin 권한 허용

					// 인증이 필요한 모든 요청은 최소 USER 권한을 갖고 있어야 한다.
					.anyRequest()
					.hasRole("USER")
			);


//		.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
//		.addFilterBefore(jwtAuthenticationFilter, BasicAuthenticationFilter.class);


//			.addFilterBefore(accessDeniedFilter, FilterSecurityInterceptor.class)

		http.addFilterBefore(new JwtAuthenticationFilter(
				authenticationManager(authenticationConfiguration), tokenProvider), UsernamePasswordAuthenticationFilter.class);

//		http.addFilterBefore(new JwtAuthorizationFilter(
//				authenticationManager(authenticationConfiguration), tokenProvider, memberRepository), BasicAuthenticationFilter.class);

		http.addFilterBefore(new JwtTokenFilter(tokenProvider), BasicAuthenticationFilter.class);
//		http.exceptionHandling(handler -> handler.authenticationEntryPoint(entryPoint));
//		http.apply(filterConfig);

		return http.build();
	}

	// @Bean
	// public RoleHierarchy roleHierarchy() {
	// 	RoleHierarchyImpl hierarchy = new RoleHierarchyImpl();
	// 	hierarchy.setHierarchy("ROLE_ADMIN > ROLE_HOST > ROLE_MANAGER > ROLE_USER");
	// 	return hierarchy;
	// }
	//
	// @Bean
	// static MethodSecurityExpressionHandler methodSecurityExpressionHandler(RoleHierarchy roleHierarchy) {
	// 	DefaultMethodSecurityExpressionHandler expressionHandler = new DefaultMethodSecurityExpressionHandler();
	// 	expressionHandler.setRoleHierarchy(roleHierarchy);
	// 	return expressionHandler;
	// }


}
