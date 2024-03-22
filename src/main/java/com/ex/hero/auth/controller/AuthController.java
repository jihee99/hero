package com.ex.hero.auth.controller;

import com.ex.hero.auth.service.LoginUseCase;
import com.ex.hero.user.dto.request.SignUpRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ex.hero.auth.service.SignupUseCase;
import com.ex.hero.common.dto.ApiResponse;
import com.ex.hero.user.dto.request.SignInRequest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping
@RequiredArgsConstructor
@Slf4j
@Tag(name = "0. 회원 가입 및 로그인 관련 API")
public class AuthController {

	private final SignupUseCase signupUseCase;

	private final LoginUseCase loginUseCase;

	@Operation(summary = "회원 가입")
	@PostMapping("/sign-up")
	public ApiResponse signUp(@RequestBody SignUpRequest request){
		return ApiResponse.success(signupUseCase.execute(request));
	}

	 @Operation(summary = "로그인")
	 @PostMapping("/login")
	 public ApiResponse signIn(@RequestBody SignInRequest request) {
	 	return ApiResponse.success(loginUseCase.execute(request));
	 }

	// @Operation(summary = "로그인")
	// @PostMapping("/login2")
	// public ResponseEntity<TokenAndUserResponse> login(@RequestBody SignInRequest signInRequest, HttpServletResponse response) {
	// 	// 로그인 시 access token, refresh token 이 생성되어 response header 에 담아 보낸다.
	// 	// rtk 는 레디스에 저장한다. 추후 atk 만료시 rtk를 이용해 atk 재발급 하기 위함
	// 	// Member member = signService.login(signInRequest);
	// 	// Token token = jwtTokenProvider.createTokenByLogin(member.getEmail(), member.getAccountRole());//atk, rtk 생성
	//
	// 	TokenAndUserResponse tokenAndUserResponse = authService.login(signInRequest);
	// 	response.addHeader(AUTHORIZATION, token.getAccessToken());// 헤더에 에세스 토큰만 싣기
	// 	return token;
	// }


}
