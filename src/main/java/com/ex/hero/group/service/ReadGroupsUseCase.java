package com.ex.hero.group.service;

import java.util.List;
import java.util.stream.Collectors;

import com.ex.hero.common.util.MemberUtils;
import com.ex.hero.group.model.dto.request.response.GroupProfileResponse;
import com.ex.hero.member.model.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class ReadGroupsUseCase {
    private final MemberUtils memberUtils;
    private final GroupService groupService;


    @Transactional(readOnly = true)
    public List<GroupProfileResponse> execute(Pageable pageable) {
        final Member member = memberUtils.getCurrentMember();
        final Long userId = member.getUserId();

        return groupService.findAllByGroupUsers_UserId(userId)
            .stream()
            .map(group -> GroupProfileResponse.of(group, userId))
            .collect(Collectors.toList());

    }

}
