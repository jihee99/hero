package com.ex.hero.common.vo;

import com.ex.hero.group.model.Group;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@Builder
public class GroupProfileVo {

	private final Long groupId;

	private final String name;

	private final String introduce;

	public static GroupProfileVo from(Group group) {
		return GroupProfileVo.builder()
			.groupId(group.getId())
			.name(group.getProfile().getName())
			.introduce(group.getProfile().getIntroduce())
			.build();
	}
}
