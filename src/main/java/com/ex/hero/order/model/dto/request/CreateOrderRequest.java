package com.ex.hero.order.model.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class CreateOrderRequest {

	@Schema(nullable = false, description = "장바구니 아이디")
	private Long cartId;

}
