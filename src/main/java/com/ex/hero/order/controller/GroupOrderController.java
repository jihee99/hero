package com.ex.hero.order.controller;

import com.ex.hero.order.model.dto.response.OrderResponse;
import com.ex.hero.order.service.ApproveOrderUseCase;
import com.ex.hero.order.service.RefuseOrderUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "6. 그룹 이벤트 주문 관리 api")
@RestController
@RequestMapping("/api/v1/events/{eventId}/orders")
@RequiredArgsConstructor
public class GroupOrderController {

    private final ApproveOrderUseCase approveOrderUseCase;
    private final RefuseOrderUseCase refuseOrderUseCase;

    @Operation(summary = "그룹 관리자가 주문을 승인합니다.")
    @PostMapping("/{order_uuid}/approve")
    public OrderResponse confirmOrder(
            @PathVariable Long eventId, @PathVariable("order_uuid") String orderUuid) {
        return approveOrderUseCase.execute(eventId, orderUuid);
    }

    @Operation(summary = "그룹 관리자가 주문을 승인 대기중인 주문을 거절합니다.")
    @PostMapping("/{order_uuid}")
    public OrderResponse refuseOrder(
            @PathVariable Long eventId, @PathVariable("order_uuid") String orderUuid) {
        return refuseOrderUseCase.execute(eventId, orderUuid);
    }
    

//    @Operation(summary = "결제 여부를 확인합니다.")
//    @PostMapping("/{order_uuid}/confirm")
//    public OrderResponse confirmOrder(
//            @PathVariable("order_uuid") String orderUuid,
//            @RequestBody ConfirmOrderRequest confirmOrderRequest) {
//        return confirmOrderUseCase.execute(orderUuid, confirmOrderRequest);
//    }

    /* 카카오페이 주문 */
}

