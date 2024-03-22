package com.ex.hero.events.service;

import com.ex.hero.common.util.UserUtils;
import com.ex.hero.events.exception.EventNotOpenException;
import com.ex.hero.events.model.Event;
import com.ex.hero.events.model.dto.response.EventDetailResponse;
import com.ex.hero.events.model.mapper.EventMapper;
import com.ex.hero.group.model.Group;
import com.ex.hero.group.service.CommonGroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReadEventDetailUseCase {

	private final UserUtils userUtils;
	private final CommonGroupService commonGroupService;
	private final CommonEventService commonEventService;
	private final EventMapper eventMapper;

	public EventDetailResponse execute(Long eventId) {
		final Event event = commonEventService.findById(eventId);
		final Group group = commonGroupService.findById(event.getGroupId());
		final Long userId = userUtils.getCurrentMemberId();
		// 호스트 유저가 아닐 경우 준비 상태일 때 조회할 수 없음
		if (event.isPreparing() && !group.isActiveGroupUserId(userId)) {
			throw EventNotOpenException.EXCEPTION;
		}
		return eventMapper.toEventDetailResponse(group, event);
	}
}
