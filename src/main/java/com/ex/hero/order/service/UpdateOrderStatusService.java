package com.ex.hero.order.service;

import com.ex.hero.order.model.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdateOrderStatusService {

    private final CommonOrderService commonOrderService;
    private final OrderValidationService orderValidator;

    public String approveExecute(String orderUuid){
        Order order = commonOrderService.findByOrderUuid(orderUuid);
        order.approve(orderValidator);
        return orderUuid;
    }

    public String refuseOrder(String orderUuid) {
        Order order = commonOrderService.findByOrderUuid(orderUuid);
        order.refuse(orderValidator);
        return orderUuid;
    }
}
