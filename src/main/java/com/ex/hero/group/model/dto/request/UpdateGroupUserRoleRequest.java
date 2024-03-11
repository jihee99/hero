package com.ex.hero.group.model.dto.request;

import java.util.UUID;

import com.ex.hero.common.annotation.Enum;
import com.ex.hero.group.model.GroupUserRole;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateGroupUserRoleRequest {
	@Schema(defaultValue = "474ecc95-bbd3-4987-8a68-590d7a06f425", description = "그룹 유저 아이디")
	@Positive(message = "올바른 유저 고유 아이디를 입력해주세요")
	private Long userId;

	@Schema(defaultValue = "MANAGER", description = "그룹 유저 역할")
	@Enum(message = "GUEST, MANAGER, MASTER 만 허용됩니다")
	private GroupUserRole role;
}
