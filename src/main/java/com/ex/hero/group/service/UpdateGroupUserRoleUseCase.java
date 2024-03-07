package com.ex.hero.group.service;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.ex.hero.group.dto.request.UpdateGroupUserRoleRequest;
import com.ex.hero.group.dto.response.GroupDetailResponse;
import com.ex.hero.group.model.Group;
import com.ex.hero.group.model.GroupUserRole;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UpdateGroupUserRoleUseCase {
	private final CommonGroupService commonGroupService;
	private final GroupService groupService;

	public GroupDetailResponse execute(UUID hostId, UpdateGroupUserRoleRequest updateGroupUserRoleRequest) {
		final Group group = commonGroupService.findById(hostId);
		final UUID updateUserId = updateGroupUserRoleRequest.getUserId();

		final GroupUserRole updateUserRole = updateGroupUserRoleRequest.getRole();
		return commonGroupService.toHostDetailResponseExecute(groupService.updateGroupUserRole(group, updateUserId, updateUserRole));
	}

}
