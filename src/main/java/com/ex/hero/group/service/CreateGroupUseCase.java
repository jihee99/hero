package com.ex.hero.group.service;

import java.util.UUID;

import com.ex.hero.member.model.MemberType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ex.hero.common.util.MemberUtils;
import com.ex.hero.group.dto.request.CreateGroupRequest;
import com.ex.hero.group.dto.response.GroupResponse;
import com.ex.hero.group.model.Group;
import com.ex.hero.group.model.GroupUserRole;
import com.ex.hero.group.model.GroupUser;
import com.ex.hero.member.model.Member;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CreateGroupUseCase {

	private final MemberUtils memberUtils;
	private final GroupService groupService;
	private final CommonGroupService commonGroupService;

	@Transactional
	public GroupResponse execute(CreateGroupRequest createGroupRequest) {
		// 존재하는 유저인지 검증
		final Member member = memberUtils.getCurrentMember();
		final Long id = member.getUserId();
		// 호스트 생성
		final Group group = groupService.createGroup( Group.toEntity(createGroupRequest, id));
		// 생성한 유저를 마스터 권한으로 등록
		final GroupUser masterGroupUser = toMasterGroupUser(group.getId(), id);

		// 즉시 활성화
		member.setAccountRole(MemberType.MASTER);
		masterGroupUser.activate();
		return GroupResponse.of(groupService.addGroupUser(group, masterGroupUser));
	}


	public GroupUser toMasterGroupUser(Long groupId, Long userId) {
		final Group group = commonGroupService.findById(groupId);
		return GroupUser.builder().userId(userId).group(group).role(GroupUserRole.MASTER).build();
	}
}
