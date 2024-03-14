package com.ex.hero.events.model.dto.response;

import com.ex.hero.common.vo.EventBasicVo;
import com.ex.hero.common.vo.EventDetailVo;
import com.ex.hero.events.model.Event;
import com.ex.hero.events.model.EventStatus;
import com.fasterxml.jackson.annotation.JsonUnwrapped;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class EventResponse {

	private Long eventId;

	private Long groupId;

	private EventStatus status;

	@JsonUnwrapped
	private EventBasicVo eventBasic;

	@JsonUnwrapped
	private EventDetailVo eventDetail;

	public static EventResponse of(Event event) {
		return EventResponse.builder()
			.eventId(event.getId())
			.groupId(event.getGroupId())
			.eventBasic(EventBasicVo.from(event))
			.eventDetail(EventDetailVo.from(event))
			.status(event.getStatus())
			.build();
	}
}
