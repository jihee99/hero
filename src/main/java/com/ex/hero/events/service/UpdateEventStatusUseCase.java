package com.ex.hero.events.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ex.hero.events.model.Event;
import com.ex.hero.events.model.EventStatus;
import com.ex.hero.events.model.dto.request.UpdateEventStatusRequest;
import com.ex.hero.events.model.dto.response.EventResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UpdateEventStatusUseCase {
	private final EventService eventService;
	private final CommonEventService commonEventService;

	@Transactional
	public EventResponse execute(Long eventId, UpdateEventStatusRequest updateEventStatusRequest) {
		final Event event = commonEventService.findById(eventId);
		final EventStatus status = updateEventStatusRequest.getStatus();

		return EventResponse.of(eventService.updateEventStatus(event, status));
	}
}
