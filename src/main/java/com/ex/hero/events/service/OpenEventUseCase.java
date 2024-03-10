package com.ex.hero.events.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ex.hero.events.model.Event;
import com.ex.hero.events.model.dto.response.EventResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OpenEventUseCase {
	private final EventService eventService;
	private final CommonEventService commonEventService;

	@Transactional
	public EventResponse execute(Long eventId) {
		final Event event = commonEventService.findById(eventId);
		return EventResponse.of(eventService.openEvent(event));
	}
}
