package com.ex.hero.group.service;

import com.ex.hero.common.dto.PageResponse;
import com.ex.hero.events.model.Event;
import com.ex.hero.events.repository.EventCustomRepository;
import com.ex.hero.events.service.CommonEventService;
import com.ex.hero.group.dto.response.GroupEventProfileResponse;
import com.ex.hero.group.model.Group;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ReadGroupEventUseCase{
    private final CommonGroupService commonGroupService;

    private final CommonEventService commonEventService;

    public PageResponse<GroupEventProfileResponse> execute(Long hostId, Pageable pageable) {
        Group group = commonGroupService.findById(hostId);
        return null;
//                commonEventService
//                        .findAllByHostId(hostId, pageable)
//                        .map(event -> GroupEventProfileResponse.of(group, event)));
    }
}
