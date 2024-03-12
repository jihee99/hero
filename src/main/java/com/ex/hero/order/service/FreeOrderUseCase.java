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


    public OrderResponse execute(String orderUuid) {
        String confirmOrderUuid = freeOrderExecute(orderUuid, memberUtils.getCurrentMemberId());
        return null;
//        return orderMapper.toOrderResponse(confirmOrderUuid);
    }

    public String freeOrderExecute(String orderUuid, Long currentUserId) {
        Order order = commonOrderService.findByOrderUuid(orderUuid);
        order.freeConfirm(currentUserId, orderValidator);
        return orderUuid;
    }
}
