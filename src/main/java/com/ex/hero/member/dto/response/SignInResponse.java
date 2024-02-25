package com.ex.hero.member.dto.response;

import com.ex.hero.member.model.MemberType;

import io.swagger.v3.oas.annotations.media.Schema;

public record SignInResponse(
        @Schema(description = "회원 이름", example = "홍길동")
        String name,
        @Schema(description = "회원 유형", example = "USER")
        MemberType role
) {
}
