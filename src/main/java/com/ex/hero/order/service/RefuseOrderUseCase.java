package com.ex.hero.order.service;

import com.ex.hero.order.model.dto.response.OrderResponse;
import com.ex.hero.order.model.mapper.OrderMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RefuseOrderUseCase {

    private final UpdateOrderStatusService updateOrderStatusService;
    private final OrderMapper orderMapper;


    public OrderResponse execute(Long eventId, String orderUuid) {
        updateOrderStatusService.refuseOrder(orderUuid);
        return orderMapper.toOrderResponse(orderUuid);
    }


}
