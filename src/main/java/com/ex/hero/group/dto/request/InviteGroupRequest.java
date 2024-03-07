package com.ex.hero.group.dto.request;

import com.ex.hero.common.annotation.Enum;

import com.ex.hero.group.model.GroupUserRole;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import lombok.Getter;

@Getter
public class InviteGroupRequest {
	@Schema(defaultValue = "user@user.com", description = "초대할 유저 이메일 주소")
	@Email(message = "올바른 이메일을 입력해주세요")
	private String email;

	@Schema(defaultValue = "MANAGER", description = "호스트 유저 역할")
	@Enum(message = "MANAGER 또는 GUEST 만 허용됩니다")
	private GroupUserRole role;
}
