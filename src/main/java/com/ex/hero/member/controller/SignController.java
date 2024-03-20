package com.ex.hero.member.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ex.hero.common.dto.ApiResponse;
import com.ex.hero.member.model.dto.request.SignInRequest;
import com.ex.hero.member.model.dto.request.SignUpRequest;
import com.ex.hero.member.service.SignService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "0. 회원 가입 및 로그인 관련 API")
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


//	@Operation(summary = "로그인")
//	@PostMapping("/login")
//	public ApiResponse signIn(@RequestBody SignInRequest request) {
//		return ApiResponse.success(signService.signIn(request));
//	}

	@Operation(summary = "회원가입 API")
	@PostMapping("/new")
	public ResponseEntity<Void> signUp3(@RequestBody SignUpRequest signUpRequest) {
		signService.signUp(signUpRequest.email(), signUpRequest.name(), signUpRequest.password());
		return ResponseEntity.ok().build();
	}
	@Operation(summary = "로그인")
	@PostMapping("/login")
	public ResponseEntity<Void> login(@RequestBody SignInRequest request) {
		return ResponseEntity.ok().build();
	}

}
