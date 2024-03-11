package com.ex.hero.group.model.dto.request;


import org.hibernate.validator.constraints.Length;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record CreateGroupRequest(

	@Schema(defaultValue = "테스트그룹", description = "그룹설명")
	@NotBlank(message = "생성할 그룹명을 입력해주세요")
	@Length(max = 15)
	String name,

	@Schema(defaultValue = "master@master.com", description = "마스터 이메일")
	@Email(message = "올바른 형식의 이메일을 입력하세요")
	String contactEmail,

	@Schema(defaultValue = "010-1234-1234", description = "마스터 전화번호")
	String contactNumber

) {
}
