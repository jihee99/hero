package com.ex.hero.events.service;

import org.springframework.stereotype.Service;

import com.ex.hero.events.model.Event;
import com.ex.hero.events.model.EventStatus;
import com.ex.hero.events.model.dto.request.UpdateEventStatusRequest;
import com.ex.hero.events.model.dto.response.EventResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class UpdateEventStatusUseCase {
	private final EventService eventService;
	private final CommonEventService commonEventService;

	public EventResponse execute(Long eventId, UpdateEventStatusRequest updateEventStatusRequest) {
		final Event event = commonEventService.findById(eventId);
		log.info("@@@@@ event {}", eventId);
		final EventStatus status = updateEventStatusRequest.getStatus();

		log.info("2@@@@@@ event status : {}", updateEventStatusRequest.getStatus());
		log.info("2@@@@@@ event status : {}", status.getName());
		return EventResponse.of(eventService.updateEventStatus(event, status));
	}
}
