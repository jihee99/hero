package com.ex.hero.order.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ex.hero.order.model.dto.request.CreateOrderRequest;
import com.ex.hero.order.model.dto.response.CreateOrderResponse;
import com.ex.hero.order.service.CreateOrderUseCase;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Tag(name = "6. 사용자 주문 관련 api")
@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {

	private final CreateOrderUseCase createOrderUseCase;

	@Operation(summary = "주문을 생성합니다. 입력한 이벤트 아이디와 티켓 아이디, 티켓 수량을 주문서로 변환합니다.")
	@PostMapping("/")
	public CreateOrderResponse createOrder(
		@RequestBody @Valid CreateOrderRequest createOrderRequest){
		return null;
		// return createOrderRequest.execute(createOrderRequest);
	}
}
