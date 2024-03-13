package com.ex.hero.order.service;

import com.ex.hero.common.util.MemberUtils;
import com.ex.hero.order.model.Order;
import com.ex.hero.order.model.dto.response.OrderResponse;
import com.ex.hero.order.model.mapper.OrderMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FreeOrderUseCase {

    private final OrderMapper orderMapper;
    private final CommonOrderService commonOrderService;
    private final MemberUtils memberUtils;
    private OrderValidationService orderValidator;


    public OrderResponse execute(String orderId) {
        String confirmOrderUuid = freeOrderExecute(orderId, memberUtils.getCurrentMemberId());
//        return null;
        return orderMapper.toOrderResponse(confirmOrderUuid);
    }

    public String freeOrderExecute(String orderId, Long currentUserId) {
        Order order = commonOrderService.findByOrderUuid(orderId);
        order.freeConfirm(currentUserId, orderValidator);
        return orderId;
    }
}
