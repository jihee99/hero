package com.ex.hero.group.service;

import com.ex.hero.group.model.dto.request.response.GroupDetailResponse;
import com.ex.hero.group.model.Group;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReadGroupUseCase {

    private final GroupService groupService;
    private final CommonGroupService commonGroupService;

    @Transactional(readOnly = true)
    public GroupDetailResponse execute(Long groupId) {
        final Group group = commonGroupService.findById(groupId);
        return commonGroupService.toGroupDetailResponseExecute(group);
    }

}
