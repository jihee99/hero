package com.ex.hero.events.service;

import org.springframework.stereotype.Service;

import com.ex.hero.events.model.mapper.EventMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SearchEventsUseCase {

	private final EventMapper eventMapper;

	// public List<EventResponse> execute(String keyword, Pageable pageable) {
	// 	return eventMapper.toEventResponseByKeyword(keyword);
	// }
}
