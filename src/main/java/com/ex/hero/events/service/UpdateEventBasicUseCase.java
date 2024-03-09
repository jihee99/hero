package com.ex.hero.events.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ex.hero.events.model.Event;
import com.ex.hero.events.model.EventBasic;
import com.ex.hero.events.model.dto.request.UpdateEventBasicRequest;
import com.ex.hero.events.model.dto.response.EventResponse;
import com.ex.hero.events.model.mapper.EventMapper;
import com.ex.hero.group.model.GroupUserRole;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UpdateEventBasicUseCase {
	private final EventService eventService;
	private final CommonEventService commonEventService;
	private final EventMapper eventMapper;

	@Transactional
	public EventResponse execute(Long eventId, UpdateEventBasicRequest updateEventBasicRequest) {
		final Event event = commonEventService.findById(eventId);
		EventBasic eventBasic = eventMapper.toEventBasic(updateEventBasicRequest);

		return EventResponse.of(eventService.updateEventBasic(event, eventBasic));
	}
}
