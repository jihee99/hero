package com.ex.hero.order.model;

import com.fasterxml.jackson.annotation.JsonValue;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum OrderMethod {
    APPROVAL("APPROVE", "승인"),
    PAYMENT("PAYMENT", "결제");

    private String value;

    @JsonValue
    private String kr;

    public Boolean isPayment() {
        System.out.println("########");
        System.out.println(this);
        // Payment > true, APPROVE > false
        return this.equals(OrderMethod.PAYMENT);
    }
}
