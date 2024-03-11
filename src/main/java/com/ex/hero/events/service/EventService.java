package com.ex.hero.events.service;

import com.ex.hero.events.exception.CannotDeleteByIssuedTicketException;
import com.ex.hero.ticket.service.CommonIssuedTicketService;
import org.springframework.stereotype.Service;

import com.ex.hero.events.exception.CannotOpenEventException;
import com.ex.hero.events.exception.UseAnotherApiException;
import com.ex.hero.events.model.Event;
import com.ex.hero.events.model.EventBasic;
import com.ex.hero.events.model.EventDetail;
import com.ex.hero.events.model.EventStatus;
import com.ex.hero.events.repository.EventRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class EventService {

	private final EventRepository eventRepository;
	private final CommonIssuedTicketService commonIssuedTicketService;

	public Event createEvent(Event event) {
		return eventRepository.save(event);
	}

	public Event updateEventBasic(Event event, EventBasic eventBasic) {
		event.setEventBasic(eventBasic);
		return eventRepository.save(event);
	}

	public Event updateEventDetail(Event event, EventDetail eventDetail) {
		event.setEventDetail(eventDetail);
		return eventRepository.save(event);
	}

	public Event openEvent(Event event) {
		this.validateEventInfoExistence(event);
		event.open();
		return eventRepository.save(event);
	}

	public Event updateEventStatus(Event event, EventStatus status) {
		if (status == EventStatus.CLOSED) event.close();
		else if (status == EventStatus.PREPARING) event.prepare();
		else throw UseAnotherApiException.EXCEPTION;
		return eventRepository.save(event);
	}

	public void validateEventInfoExistence(Event event) {
		if (!event.hasEventInfo()) throw CannotOpenEventException.EXCEPTION;
	}

	public Event deleteEvent(Event event) {
		// 발급된 티켓이 있다면 삭제 불가
		if (commonIssuedTicketService.existsByEventId(event.getId()))
			throw CannotDeleteByIssuedTicketException.EXCEPTION;
		event.deleteSoft();
		return eventRepository.save(event);
	}
}
