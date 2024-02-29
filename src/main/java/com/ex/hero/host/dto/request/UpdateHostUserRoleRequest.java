package com.ex.hero.host.dto.request;

import java.util.UUID;

import com.ex.hero.common.annotation.Enum;
import com.ex.hero.host.model.HostRole;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateHostUserRoleRequest {
	@Schema(defaultValue = "474ecc95-bbd3-4987-8a68-590d7a06f425", description = "호스트 유저 아이디")
	@Positive(message = "올바른 유저 고유 아이디를 입력해주세요")
	private UUID userId;

	@Schema(defaultValue = "MANAGER", description = "호스트 유저 역할")
	@Enum(message = "GUEST, MANAGER, MASTER 만 허용됩니다")
	private HostRole role;
}
