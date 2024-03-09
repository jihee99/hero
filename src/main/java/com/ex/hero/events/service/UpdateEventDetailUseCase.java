package com.ex.hero.events.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ex.hero.events.model.Event;
import com.ex.hero.events.model.dto.request.UpdateEventDetailRequest;
import com.ex.hero.events.model.dto.response.EventResponse;
import com.ex.hero.events.model.mapper.EventMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UpdateEventDetailUseCase {

	private final EventService eventService;
	private final CommonEventService commonEventService;
	private final EventMapper eventMapper;

	@Transactional
	public EventResponse execute(Long eventId, UpdateEventDetailRequest updateEventDetailRequest) {
		final Event event = commonEventService.findById(eventId);
		return EventResponse.of(
			eventService.updateEventDetail(
				event, eventMapper.toEventDetail(updateEventDetailRequest)));
	}
}
