package com.ex.hero.events.model.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class UpdateEventDetailRequest {

	// TODO 이미지 고민

	// 전시 상세 내용
	@Schema(defaultValue = "전시 상세 내용을 입력해주세요.", description = "전시 상세 내용")
	@NotBlank(message = "전시 상세 내용을 입력하세요")
	private String content;
}
