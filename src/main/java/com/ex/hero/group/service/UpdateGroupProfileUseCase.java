package com.ex.hero.group.service;

import com.ex.hero.group.model.Group;
import com.ex.hero.group.model.GroupProfile;
import com.ex.hero.group.model.dto.request.UpdateGroupRequest;
import com.ex.hero.group.model.dto.request.response.GroupDetailResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UpdateGroupProfileUseCase {
	private final CommonGroupService commonGroupService;
	private final GroupService groupService;

	@Transactional
	public GroupDetailResponse execute(Long groupId, UpdateGroupRequest updateGroupRequest) {
		final Group group = commonGroupService.findById(groupId);
		return commonGroupService.toGroupDetailResponseExecute(
			groupService.updateGroupProfile(group, toGroupProfile(updateGroupRequest)));
	}

	public GroupProfile toGroupProfile(UpdateGroupRequest updateGroupRequest) {
		return GroupProfile.builder()
			.introduce(updateGroupRequest.getIntroduce())
			.contactEmail(updateGroupRequest.getContactEmail())
			.contactNumber(updateGroupRequest.getContactNumber())
			.build();
	}
}
