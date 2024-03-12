package com.ex.hero.order.model;

import com.ex.hero.common.vo.Money;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PaymentInfo {
    @Embedded
    @AttributeOverride(name = "amount", column = @Column(name = "payment_amount"))
    private Money paymentAmount;

    @Embedded
    @AttributeOverride(name = "amount", column = @Column(name = "supply_amount"))
    private Money supplyAmount;

    @Builder
    public PaymentInfo(Money paymentAmount, Money supplyAmount) {
        this.paymentAmount = paymentAmount;
        this.supplyAmount = supplyAmount;
    }
}
