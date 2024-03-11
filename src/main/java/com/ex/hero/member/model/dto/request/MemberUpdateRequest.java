package com.ex.hero.member.model.dto.request;

import com.ex.hero.member.model.MemberType;

import io.swagger.v3.oas.annotations.media.Schema;

public record MemberUpdateRequest(
        @Schema(description = "회원 비밀번호", example = "1234")
        String password,
        @Schema(description = "회원 새 비밀번호", example = "1234")
        String newPassword,
        @Schema(description = "회원 이름", example = "홍길동")
        String name,
        @Schema(description = "회원 나이", example = "user@test.com")
        String email,
        @Schema(description = "회원 전화번호", example = "010-1234-1234")
        String phoneNumber,
        @Schema(description = "회원 등급", example = "USER")
        MemberType role
) {
}
