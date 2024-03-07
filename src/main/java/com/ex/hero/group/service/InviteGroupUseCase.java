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

	public GroupDetailResponse execute(UUID hostId, InviteGroupRequest inviteGroupRequest) {
		final Group group = commonGroupService.findById(hostId);
		final Member inviteMember = commonMemberService.queryUserByEmail(inviteGroupRequest.getEmail());
		final UUID invitedUserId = inviteMember.getUserId();
		final GroupUserRole role = inviteGroupRequest.getRole();

		final GroupUser groupUser = toHostUser(hostId, invitedUserId, role);
		 invitationEmailService.execute(inviteMember.toEmailUserInfo(), group.toHostProfileVo().getName(), role);

		return commonGroupService.toHostDetailResponseExecute(groupService.inviteGroupUser(group, groupUser));
	}

	public GroupUser toHostUser(UUID hostId, UUID userId, GroupUserRole role) {
		final Group group = commonGroupService.findById(hostId);
		return GroupUser.builder().userId(userId).group(group).role(role).build();
	}


}
