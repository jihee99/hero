package com.ex.hero.events.model.dto.request;

import com.ex.hero.events.model.EventStatus;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class UpdateEventStatusRequest {
	@Schema(defaultValue = "OPEN", description = "오픈 상태")
	private EventStatus status;
}
