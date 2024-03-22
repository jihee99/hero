package com.ex.hero.events.service;

import com.ex.hero.events.model.dto.response.EventResponse;
import com.ex.hero.events.model.mapper.EventMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReadEventsUseCase {
	private final EventMapper eventMapper;

	public List<EventResponse> execute() {
		return eventMapper.toEventResponse();
	}

}
