package com.ex.hero.group.service;


import com.ex.hero.events.service.CommonEventService;
import com.ex.hero.group.model.Group;
import com.ex.hero.group.model.dto.request.response.GroupEventProfileResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ReadGroupEventUseCase{
    private final CommonGroupService commonGroupService;

     private final CommonEventService commonEventService;

    public List<GroupEventProfileResponse> execute(Long groupId) {
        Group group = commonGroupService.findById(groupId);
        return commonEventService.findAllByGroupId(groupId)
                .stream().map(event -> GroupEventProfileResponse.of(group, event))
                .toList();
    }
}
