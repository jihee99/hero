package com.ex.hero.events.model.mapper;

import java.util.List;
import java.util.stream.Collectors;

import com.ex.hero.common.annotation.Mapper;
import com.ex.hero.events.model.Event;
import com.ex.hero.events.model.EventBasic;
import com.ex.hero.events.model.EventDetail;
import com.ex.hero.events.model.dto.request.CreateEventRequest;
import com.ex.hero.events.model.dto.request.UpdateEventBasicRequest;
import com.ex.hero.events.model.dto.request.UpdateEventDetailRequest;
import com.ex.hero.events.model.dto.response.EventDetailResponse;
import com.ex.hero.events.model.dto.response.EventResponse;
import com.ex.hero.events.service.CommonEventService;
import com.ex.hero.group.model.Group;
import com.ex.hero.group.service.CommonGroupService;
import com.ex.hero.group.service.GroupService;

import lombok.RequiredArgsConstructor;

@Mapper
@RequiredArgsConstructor
public class EventMapper {

	private final GroupService groupService;
	private final CommonGroupService commonGroupService;

	private final CommonEventService commonEventService;

	public Event toEntity(CreateEventRequest createEventRequest) {
		return Event.builder()
			.groupId(createEventRequest.getGroupId())
			.name(createEventRequest.getName())
			.startAt(createEventRequest.getStartAt())
			.runTime(createEventRequest.getRunTime())
			.build();
	}

	public List<EventResponse> toEventResponse(){
		return commonEventService.findAllByOrderByCreatedAtDesc()
			.stream()
			.map(EventResponse::of)
			.collect(Collectors.toList());
	}

	public EventDetail toEventDetail(UpdateEventDetailRequest updateEventDetailRequest) {
		return EventDetail.builder()
			.content(updateEventDetailRequest.getContent())
			.build();
	}

	public EventDetailResponse toEventDetailResponse(Group group, Event event) {
		return EventDetailResponse.of(group, event);
	}

	public EventBasic toEventBasic(UpdateEventBasicRequest updateEventBasicRequest) {
		return EventBasic.builder()
			.name(updateEventBasicRequest.getName())
			.runTime(updateEventBasicRequest.getRunTime())
			.startAt(updateEventBasicRequest.getStartAt())
			.build();
	}


}
