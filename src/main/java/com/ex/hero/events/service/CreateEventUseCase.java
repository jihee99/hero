package com.ex.hero.events.service;

import com.ex.hero.common.util.UserUtils;
import com.ex.hero.events.model.Event;
import com.ex.hero.events.model.dto.request.CreateEventRequest;
import com.ex.hero.events.model.dto.response.EventResponse;
import com.ex.hero.events.model.mapper.EventMapper;
import com.ex.hero.group.service.GroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateEventUseCase {
    private final UserUtils userUtils;
    private final GroupService groupService;
    private final EventService eventService;
    private final EventMapper eventMapper;

    public EventResponse execute(CreateEventRequest createEventRequest) {
        final Long userId = userUtils.getCurrentMemberId();
        // 매니저 이상만 생성 가능
        groupService.validateManagerGroupUser(createEventRequest.getGroupId(), userId);
        final Event event = eventMapper.toEntity(createEventRequest);
        return EventResponse.of(eventService.createEvent(event));
    }
}
