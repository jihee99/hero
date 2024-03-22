package com.ex.hero.group.service;


import com.ex.hero.group.model.Group;
import com.ex.hero.group.model.GroupUser;
import com.ex.hero.group.model.GroupUserRole;
import com.ex.hero.group.model.dto.request.InviteGroupRequest;
import com.ex.hero.group.model.dto.request.response.GroupDetailResponse;
import com.ex.hero.mail.service.GroupUserInvitationEmailService;
import com.ex.hero.user.model.User;
import com.ex.hero.user.service.CommonUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InviteGroupUseCase {

	private final CommonGroupService commonGroupService;
	private final GroupService groupService;
	private final CommonUserService commonUserService;
	private final GroupUserInvitationEmailService invitationEmailService;

	public GroupDetailResponse execute(Long groupId, InviteGroupRequest inviteGroupRequest) {
		final Group group = commonGroupService.findById(groupId);
		final User inviteUser = commonUserService.queryUserByEmail(inviteGroupRequest.getEmail());
		final Long invitedUserId = inviteUser.getUserId();
		final GroupUserRole role = inviteGroupRequest.getRole();

		final GroupUser groupUser = toGroupUser(groupId, invitedUserId, role);
//		 invitationEmailService.execute(inviteMember.toEmailUserInfo(), group.toGroupProfileVo().getName(), role);

		return commonGroupService.toGroupDetailResponseExecute(groupService.inviteGroupUser(group, groupUser));
	}

	public GroupUser toGroupUser(Long groupId, Long userId, GroupUserRole role) {
		final Group group = commonGroupService.findById(groupId);
		return GroupUser.builder().userId(userId).group(group).role(role).build();
	}


}
