package com.ex.hero.order.model.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class CreateOrderRequest {

	@Schema(nullable = false, description = "구매할 쿠폰 아이디")
	private Long ticketId;

	@Schema(nullable = false, defaultValue = "1")
	private Long quantity;

}
