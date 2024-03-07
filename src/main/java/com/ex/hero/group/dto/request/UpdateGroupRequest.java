package com.ex.hero.group.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class UpdateGroupRequest {

    @Schema(defaultValue = "안녕하세요 소개글 입니다.", description = "그룹 간단 소개")
    @NotNull(message = "간단 소개를 입력해주세요")
    private String introduce;

    @Schema(defaultValue = "010-1234-1234", description = "마스터 전화번호")
    private String contactNumber;

    @Schema(defaultValue = "master@master.com", description = "마스터 이메일")
    @Email(message = "올바른 형식의 이메일을 입력하세요")
    private String contactEmail;
}
