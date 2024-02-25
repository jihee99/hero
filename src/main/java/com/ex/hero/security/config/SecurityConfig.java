package com.ex.hero.security.config;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

	private final String[] allowedUrls = {"/", "/swagger-ui/**", "/v3/**", "/sign-up", "/sign-in"};

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
			.sessionManagement( sessionManageMent -> sessionManageMent.sessionCreationPolicy(
				SessionCreationPolicy.STATELESS))

			.csrf(AbstractHttpConfigurer::disable)

			.authorizeHttpRequests(requests ->
				requests.requestMatchers(allowedUrls).permitAll()	// requestMatchers의 인자로 전달된 url은 모두에게 허용
					.anyRequest().authenticated()	// 그 외의 모든 요청은 인증 필요
			)

		;

		return http.build();
	}
}
