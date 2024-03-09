package com.ex.hero.events.model.dto.request;

import java.time.LocalDateTime;

import org.hibernate.validator.constraints.Length;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CreateEventRequest {
	@Schema(defaultValue = "1", description = "그룹 고유 아이디")
	@Positive
	private Long groupId;

	@Schema(defaultValue = "테스트용 전시회명", description = "전시명")
	@NotBlank(message = "전시회명을 입력하세요.")
	@Length(max = 25)
	private String name;

	@Schema(
		type = "string",
		pattern = "yyyy.MM.dd",
		defaultValue = "2024.03.01",
		description = "전시 시작 시각")
	@NotNull(message = "전시 시작일을 입력하세요.")
	@Future(message = "전시 시작일은 현재보다 이후여야 합니다.")
	private LocalDateTime startAt;

	@Schema(defaultValue = "30", description = "소요시간")
	@Positive(message = "예상 소요시간(분)을 입력하세요.")
	private Long runTime;
}
