package com.ex.hero.events.model.dto.response;

import com.ex.hero.common.vo.EventBasicVo;
import com.ex.hero.common.vo.GroupInfoVo;
import com.ex.hero.events.model.Event;
import com.ex.hero.events.model.EventStatus;
import com.ex.hero.group.model.Group;
import com.fasterxml.jackson.annotation.JsonUnwrapped;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class EventDetailResponse {
	private EventStatus status;
	private GroupInfoVo group;
	@JsonUnwrapped
	private EventBasicVo eventBasicVo;

	public static EventDetailResponse of(Group group, Event event) {
		return EventDetailResponse.builder()
			.eventBasicVo(event.toEventBasicVo())
			.group(group.toGroupInfoVo())
			.status(event.getStatus())
			.build();
	}
}
