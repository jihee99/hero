package com.ex.hero.group.service;

import java.util.*;
import java.util.stream.Collectors;

import com.ex.hero.group.model.GroupUserRole;
import com.ex.hero.group.repository.GroupRepository;
import org.springframework.stereotype.Service;

import com.ex.hero.group.dto.response.GroupDetailResponse;
import com.ex.hero.group.exception.GroupNotFoundException;
import com.ex.hero.group.model.Group;
import com.ex.hero.group.model.GroupUser;
import com.ex.hero.domains.common.vo.GroupUserVo;
import com.ex.hero.member.model.Member;
import com.ex.hero.member.repository.MemberRepository;
import com.ex.hero.member.vo.MemberInfoVo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CommonGroupService {

	private final MemberRepository memberRepository;
	private final GroupRepository groupRepository;

	public Group findById(Long groupId) {
		return groupRepository.findById(groupId).orElseThrow(() -> GroupNotFoundException.EXCEPTION);
	}

	public GroupUser toGroupUser(Long groupId, Long userId, GroupUserRole role) {
		final Group group = groupRepository.findById(groupId).orElseThrow(() -> GroupNotFoundException.EXCEPTION);
		return GroupUser.builder().userId(userId).group(group).role(role).build();
	}

	public GroupDetailResponse toGroupDetailResponseExecute(Group group) {
		final List<Long> userIds = group.getGroupUser_UserIds();
		final List<Member> userList = memberRepository.findAllByUserIdIn(userIds);
		final Map<Long, Member> userMap =
			userList.stream().collect(Collectors.toMap(Member::getUserId, user -> user));
		final List<GroupUserVo> groupUserVoList = new ArrayList<>();

		for (Long userId : userIds) {
			final Member user = userMap.get(userId);
			if (user != null) {
				final MemberInfoVo memberInfoVo = user.toMemberInfoVo();
				final GroupUser groupUser = group.getGroupUserByUserId(userId);
				final GroupUserVo groupUserVo = GroupUserVo.from(memberInfoVo, groupUser);
				groupUserVoList.add(groupUserVo);
			}
		}
		return GroupDetailResponse.of(group, groupUserVoList);
	}

}
