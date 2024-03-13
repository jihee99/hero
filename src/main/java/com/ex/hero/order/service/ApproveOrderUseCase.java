package com.ex.hero.order.service;

import com.ex.hero.order.model.dto.response.OrderResponse;
import com.ex.hero.order.model.mapper.OrderMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ApproveOrderUseCase {

    private final UpdateOrderStatusService updateOrderStatusService;
    private final OrderMapper orderMapper;

    public OrderResponse execute(Long eventId, String orderUuid) {
        String confirmOrderUuid = updateOrderStatusService.approveExecute(orderUuid);
        return orderMapper.toOrderResponse(confirmOrderUuid);
    }

}
