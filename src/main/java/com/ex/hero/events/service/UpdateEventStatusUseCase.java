package com.ex.hero.events.service;

import com.ex.hero.events.model.Event;
import com.ex.hero.events.model.EventStatus;
import com.ex.hero.events.model.dto.request.UpdateEventStatusRequest;
import com.ex.hero.events.model.dto.response.EventResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UpdateEventStatusUseCase {
	private final EventService eventService;
	private final CommonEventService commonEventService;

	public EventResponse execute(Long eventId, UpdateEventStatusRequest updateEventStatusRequest) {
		final Event event = commonEventService.findById(eventId);
		final EventStatus status = updateEventStatusRequest.getStatus();

		return EventResponse.of(eventService.updateEventStatus(event, status));
	}
}
