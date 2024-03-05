package com.ex.hero.member.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

import com.ex.hero.member.model.Member;

public record SignUpResponse(
        @Schema(description = "회원 고유키", example = "c0a80121-7aeb-4b4b-8b7a-9b9b9b9b9b9b")
        UUID userId,
        @Schema(description = "회원 아이디", example = "user@user.com")
        String email,
        @Schema(description = "회원 이름", example = "홍길동")
        String name
) {
    public static SignUpResponse from(Member member) {
        return new SignUpResponse(
                member.getUserId(),
                member.getEmail(),
                member.getName()
        );
    }
}
