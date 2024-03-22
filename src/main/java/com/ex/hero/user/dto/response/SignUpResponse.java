package com.ex.hero.user.dto.response;

import com.ex.hero.user.model.User;

import io.swagger.v3.oas.annotations.media.Schema;

public record SignUpResponse(
	@Schema(description = "회원 고유키", example = "c0a80121-7aeb-4b4b-8b7a-9b9b9b9b9b9b")
	Long userId,
	@Schema(description = "회원 아이디", example = "user@test.com")
	String email,
	@Schema(description = "회원 이름", example = "홍길동")
	String name
) {
	public static SignUpResponse from(User user) {
		return new SignUpResponse(
			user.getUserId(),
			user.getEmail(),
			user.getName()
		);
	}
}
