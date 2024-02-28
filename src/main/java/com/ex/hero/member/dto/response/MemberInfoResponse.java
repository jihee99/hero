package com.ex.hero.member.dto.response;

import java.time.LocalDateTime;
import java.util.UUID;

import com.ex.hero.member.model.Member;
import com.ex.hero.member.model.MemberType;

import io.swagger.v3.oas.annotations.media.Schema;

public record MemberInfoResponse(
	@Schema(description = "회원 고유키", example = "c0a80121-7aeb-4b4b-8b0a-6b1c032f0e4a")
	UUID id,
	@Schema(description = "회원 아이디", example = "gildong2")
	String account,
	@Schema(description = "회원 이름", example = "홍길동")
	String name,
	@Schema(description = "회원 이메일", example = "user@test.com")
	String email,
	@Schema(description = "회원 타입", example = "USER")
	MemberType role,
	@Schema(description = "회원 생성일", example = "2023-05-11T15:00:00")
	LocalDateTime createdAt
) {
	public static MemberInfoResponse from(Member member) {
		return new MemberInfoResponse(
			member.getId(),
			member.getAccount(),
			member.getName(),
			member.getEmail(),
			member.getRole(),
			member.getCreatedAt()
		);
	}
}
