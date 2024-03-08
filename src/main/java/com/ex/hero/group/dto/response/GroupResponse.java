package com.ex.hero.group.dto.response;

import com.ex.hero.group.model.Group;
import com.ex.hero.domains.common.vo.GroupInfoVo;
import com.fasterxml.jackson.annotation.JsonUnwrapped;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GroupResponse {
	@Schema(description = "그룹 프로필")
	@JsonUnwrapped
	private final GroupInfoVo profile;

	@Schema(description = "마스터 유저의 고유 아이디")
	private final Long masterUserId;


	public static GroupResponse of(Group group) {
		return GroupResponse.builder()
			.profile(GroupInfoVo.from(group))
			.masterUserId(group.getMasterUserId())
			.build();
	}
}
