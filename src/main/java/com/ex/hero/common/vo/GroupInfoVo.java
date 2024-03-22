package com.ex.hero.common.vo;


import com.ex.hero.group.model.Group;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
@Builder
public class GroupInfoVo {
	private final Long groupId;

	private final String name;

	private final String introduce;


	private final String contactEmail;

	private final String contactNumber;

	private final Boolean partner;

	public static GroupInfoVo from(Group group) {
		return GroupInfoVo.builder()
			.groupId(group.getId())
			.name(group.getProfile().getName())
			.introduce(group.getProfile().getIntroduce())
			.contactEmail(group.getProfile().getContactEmail())
			.contactNumber(group.getProfile().getContactNumber())
			.build();
	}
}
