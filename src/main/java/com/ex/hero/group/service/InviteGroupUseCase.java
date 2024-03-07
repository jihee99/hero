package com.ex.hero.group.service;


import java.util.UUID;

import com.ex.hero.member.service.CommonMemberService;
import org.springframework.stereotype.Service;

import com.ex.hero.group.dto.request.InviteGroupRequest;
import com.ex.hero.group.dto.response.GroupDetailResponse;
import com.ex.hero.group.model.Group;
import com.ex.hero.group.model.GroupUserRole;
import com.ex.hero.group.model.GroupUser;
import com.ex.hero.member.model.Member;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class InviteGroupUseCase {

	private final CommonGroupService commonGroupService;
	private final GroupService groupService;
	private final CommonMemberService commonMemberService;
	private final GroupUserInvitationEmailService invitationEmailService;

	public GroupDetailResponse execute(Long groupId, InviteGroupRequest inviteGroupRequest) {
		final Group group = commonGroupService.findById(groupId);
		final Member inviteMember = commonMemberService.queryUserByEmail(inviteGroupRequest.getEmail());
		final Long invitedUserId = inviteMember.getUserId();
		final GroupUserRole role = inviteGroupRequest.getRole();

		final GroupUser groupUser = toGroupUser(groupId, invitedUserId, role);
		 invitationEmailService.execute(inviteMember.toEmailUserInfo(), group.toGroupProfileVo().getName(), role);

		return commonGroupService.toGroupDetailResponseExecute(groupService.inviteGroupUser(group, groupUser));
	}

	public GroupUser toGroupUser(Long groupId, Long userId, GroupUserRole role) {
		final Group group = commonGroupService.findById(groupId);
		return GroupUser.builder().userId(userId).group(group).role(role).build();
	}


}
