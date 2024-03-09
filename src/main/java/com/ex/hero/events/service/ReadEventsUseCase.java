package com.ex.hero.events.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ex.hero.events.model.dto.response.EventResponse;
import com.ex.hero.events.model.mapper.EventMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReadEventsUseCase {
	private final EventMapper eventMapper;

	public List<EventResponse> execute() {
		return eventMapper.toEventResponse();
	}

}
