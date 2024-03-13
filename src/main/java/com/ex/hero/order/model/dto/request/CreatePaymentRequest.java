package com.ex.hero.order.model.dto.request;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CreatePaymentRequest {
    private final String method;
    private final Long amount;
    private final String orderId;
    private final String orderName;
    private final String successUrl;
    private final String failUrl;
}
