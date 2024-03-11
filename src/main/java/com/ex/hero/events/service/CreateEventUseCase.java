package com.ex.hero.events.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ex.hero.common.util.MemberUtils;
import com.ex.hero.events.model.Event;
import com.ex.hero.events.model.dto.request.CreateEventRequest;
import com.ex.hero.events.model.dto.response.EventResponse;
import com.ex.hero.events.model.mapper.EventMapper;
import com.ex.hero.group.model.Group;
import com.ex.hero.group.service.GroupService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CreateEventUseCase {
    private final MemberUtils memberUtils;
    private final GroupService groupService;
    private final EventService eventService;
    private final EventMapper eventMapper;

    public EventResponse execute(CreateEventRequest createEventRequest) {
        final Long userId = memberUtils.getCurrentMemberId();
        // 매니저 이상만 생성 가능
        groupService.validateManagerGroupUser(createEventRequest.getGroupId(), userId);
        final Event event = eventMapper.toEntity(createEventRequest);
        return EventResponse.of(eventService.createEvent(event));
    }
}