package com.ex.hero.member.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

public record SignInRequest(
        @Schema(description = "회원 아이디", example = "gildong2")
        String account,
        @Schema(description = "회원 비밀번호", example = "1234")
        String password
) {
}
