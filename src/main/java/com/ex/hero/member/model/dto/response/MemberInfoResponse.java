package com.ex.hero.member.model.dto.response;

import java.time.LocalDateTime;

import com.ex.hero.member.model.Member;
import com.ex.hero.member.model.MemberType;

import io.swagger.v3.oas.annotations.media.Schema;

public record MemberInfoResponse(
	@Schema(description = "회원 고유키", example = "c0a80121-7aeb-4b4b-8b0a-6b1c032f0e4a")
	Long userId,
	@Schema(description = "회원 이메일", example = "user@test.com")
	String email,
	@Schema(description = "회원 이름", example = "홍길동")
	String name,
	@Schema(description = "회원 타입", example = "USER")
	MemberType role,
	@Schema(description = "회원 생성일", example = "2023-05-11T15:00:00")
	LocalDateTime createdAt
) {
	public static MemberInfoResponse from(Member member) {
		return new MemberInfoResponse(
			member.getUserId(),
			member.getEmail(),
			member.getName(),
			member.getAccountRole(),
			member.getCreatedAt()
		);
	}
}
