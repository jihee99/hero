package com.ex.hero.ticket.model.dto.request;

import com.ex.hero.common.annotation.Enum;

import com.ex.hero.ticket.model.TicketPayType;
import com.ex.hero.ticket.model.TicketType;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class CreateTicketItemRequest {

	@Schema(nullable = false, defaultValue = "무료")
	@Enum(message = "[무료/유료]만 허용됩니다.")
	private TicketPayType payType;

	@NotEmpty(message = "티켓상품 이름을 입력해주세요")
	@Schema(nullable = false, example = "일반 티켓")
	private String name;

	@Schema(nullable = true, example = "티켓 설명을 입력하세요.")
	@Nullable
	private String description;

	@NotNull
	@Schema(defaultValue = "0", nullable = false, example = "4000")
	private Long price;

	@NotNull
	@Schema(nullable = false, example = "100")
	private Long supplyCount;

	@Schema(nullable = false, defaultValue = "승인")
	@Enum(message = "[승인/선착순]만 허용됩니다.")
	private TicketType approveType;

	@NotNull
	@Schema(nullable = false, example = "1")
	private Long purchaseLimit;


}
