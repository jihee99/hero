package com.ex.hero.member.dto.response;


import com.ex.hero.member.model.Member;

import io.swagger.v3.oas.annotations.media.Schema;

public record MemberUpdateResponse(
        @Schema(description = "회원 정보 수정 성공 여부", example = "true")
        boolean result,
        @Schema(description = "회원 이름", example = "홍길동")
        String name,
        @Schema(description = "회원 이메일", example = "user@test.com")
        String email,
        @Schema(description = "회원 전화번호", example = "010-1234-1234")
        String phone
) {
    public static MemberUpdateResponse of(boolean result, Member member) {
        return new MemberUpdateResponse(result, member.getName(), member.getEmail(), member.getPhone());
    }
}
