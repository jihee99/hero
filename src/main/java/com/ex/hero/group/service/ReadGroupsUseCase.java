package com.ex.hero.group.service;

import com.ex.hero.common.util.UserUtils;
import com.ex.hero.group.model.dto.request.response.GroupProfileResponse;
import com.ex.hero.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class ReadGroupsUseCase {
    private final UserUtils userUtils;
    private final GroupService groupService;


    @Transactional(readOnly = true)
    public List<GroupProfileResponse> execute() {
        final User user = userUtils.getCurrentMember();
        final Long userId = user.getUserId();

        return groupService.findAllByGroupUsers_UserId(userId)
            .stream()
            .map(group -> GroupProfileResponse.of(group, userId))
            .collect(Collectors.toList());

    }

}
