package com.ex.hero.group.service;

import java.util.UUID;

import com.ex.hero.group.repository.GroupRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ex.hero.group.dto.request.UpdateGroupRequest;
import com.ex.hero.group.dto.response.GroupDetailResponse;
import com.ex.hero.group.model.Group;
import com.ex.hero.group.model.GroupProfile;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UpdateGroupProfileUseCase {
	private final CommonGroupService commonGroupService;
	private final GroupService groupService;
	private final GroupRepository groupRepository;

	@Transactional
	public GroupDetailResponse execute(Long groupId, UpdateGroupRequest updateGroupRequest) {
		final Group group = commonGroupService.findById(groupId);

		return commonGroupService.toGroupDetailResponseExecute(
			groupService.updateGroupProfile(group, toGroupprofile(updateGroupRequest)));
	}

	public GroupProfile toGroupprofile(UpdateGroupRequest updateGroupRequest) {
		return GroupProfile.builder()
			.introduce(updateGroupRequest.getIntroduce())
			.contactEmail(updateGroupRequest.getContactEmail())
			.contactNumber(updateGroupRequest.getContactNumber())
			.build();
	}
}
