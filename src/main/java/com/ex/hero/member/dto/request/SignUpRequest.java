package com.ex.hero.member.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

public record SignUpRequest(
        @Schema(description = "회원 이메일", example = "user@test.com")
        String email,
        @Schema(description = "회원 비밀번호", example = "1234")
        String password,
        @Schema(description = "회원 이름", example = "홍길동")
        String name
){
}
