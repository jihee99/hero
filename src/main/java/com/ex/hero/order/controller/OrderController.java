package com.ex.hero.order.controller;

import com.ex.hero.order.model.dto.response.OrderResponse;
import com.ex.hero.order.service.FreeOrderUseCase;
import org.springframework.web.bind.annotation.*;

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
	private final FreeOrderUseCase freeOrderUseCase;
	@Operation(summary = "주문을 생성합니다. 입력한 이벤트 아이디와 티켓 아이디, 티켓 수량을 주문서로 변환합니다.")
	@PostMapping("/")
	public CreateOrderResponse createOrder(
		@RequestBody @Valid CreateOrderRequest createOrderRequest){
		 return createOrderUseCase.execute(createOrderRequest);
	}

	@Operation(summary = "주문을 무료로 결제합니다. 선착순 방식 결제 0원일 때 지원")
	@PostMapping("/{orderId}/free")
	public OrderResponse freeOrder(@PathVariable("orderId") String orderId) {
		return freeOrderUseCase.execute(orderId);
	}

}
