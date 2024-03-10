package com.ex.hero.events.controller;

import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ex.hero.events.model.dto.request.CreateEventRequest;
import com.ex.hero.events.model.dto.request.UpdateEventBasicRequest;
import com.ex.hero.events.model.dto.request.UpdateEventDetailRequest;
import com.ex.hero.events.model.dto.request.UpdateEventStatusRequest;
import com.ex.hero.events.model.dto.response.EventResponse;
import com.ex.hero.events.service.CreateEventUseCase;
import com.ex.hero.events.service.OpenEventUseCase;
import com.ex.hero.events.service.UpdateEventBasicUseCase;
import com.ex.hero.events.service.UpdateEventDetailUseCase;
import com.ex.hero.events.service.UpdateEventStatusUseCase;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@Tag(name = "이벤트 관리 API(그룹용)")
@RequestMapping("/api/v1/manager")
@RequiredArgsConstructor
public class GroupEventController {

	private final CreateEventUseCase createEventUseCase;
 	private final UpdateEventBasicUseCase updateEventBasicUseCase;
 	private final UpdateEventDetailUseCase updateEventDetailUseCase;
 	private final UpdateEventStatusUseCase updateEventStatusUseCase;
	private final OpenEventUseCase openEventUseCase;

	@Operation(summary = "전시 기본 정보를 등록하여, 새로운 이벤트를 생성합니다.")
	@PostMapping
	public EventResponse createEvent(@RequestBody @Valid CreateEventRequest createEventRequest) {
		return createEventUseCase.execute(createEventRequest);
	}

	@Operation(summary = "전시 정보를 수정합니다.")
	@PostMapping("/{eventId}/basic")
	public EventResponse updateEventBasic(
		@PathVariable Long eventId,
		@RequestBody @Valid UpdateEventBasicRequest updateEventBasicRequest) {
		return updateEventBasicUseCase.execute(eventId, updateEventBasicRequest);
	}

	@Operation(summary = "전시 상세 정보를 수정합니다.")
	@PostMapping("/{eventId}/details")
	public EventResponse updateEventDetail(
		@PathVariable Long eventId,
		@RequestBody @Valid UpdateEventDetailRequest updateEventDetailRequest) {
		return updateEventDetailUseCase.execute(eventId, updateEventDetailRequest);
	}

	@Operation(summary = "공연을 오픈 상태로 변경합니다. 모든 체크리스트를 달성해야 합니다.")
	@PostMapping("/{eventId}/open")
	public EventResponse updateEventStatus(@PathVariable Long eventId) {
		return openEventUseCase.execute(eventId);
	}

	@Operation(summary = "전시 상태를 변경합니다. (OPEN 제외)")
	@PostMapping("/{eventId}/status")
	public EventResponse updateEventStatus(
		@PathVariable Long eventId,
		@RequestBody @Valid UpdateEventStatusRequest updateEventDetailRequest) {
		System.out.println("@@@");
		return updateEventStatusUseCase.execute(eventId, updateEventDetailRequest);
	}




}
