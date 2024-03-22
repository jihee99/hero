package com.ex.hero.events.service;

import com.ex.hero.events.model.mapper.EventMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SearchEventsUseCase {

	private final EventMapper eventMapper;

	// public List<EventResponse> execute(String keyword, Pageable pageable) {
	// 	return eventMapper.toEventResponseByKeyword(keyword);
	// }
}
