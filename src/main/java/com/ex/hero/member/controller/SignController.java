package com.ex.hero.member.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ex.hero.common.dto.ApiResponse;
import com.ex.hero.member.dto.signin.request.SignInRequest;
import com.ex.hero.member.dto.signup.request.SignUpRequest;
import com.ex.hero.member.service.SignService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "회원 가입 및 로그인 관련 API")
@RequiredArgsConstructor
@RestController
@RequestMapping
public class SignController {

	private final SignService signService;

	@Operation(summary = "회원 가입")
	@PostMapping("/sign-up")
	public ApiResponse signUp(@RequestBody SignUpRequest request){
		return ApiResponse.success(signService.registerMember(request));
	}

	@Operation(summary = "로그인")
	@PostMapping("/sign-in")
	public ApiResponse signIn(@RequestBody SignInRequest request) {
		return ApiResponse.success(signService.signIn(request));
	}

}
