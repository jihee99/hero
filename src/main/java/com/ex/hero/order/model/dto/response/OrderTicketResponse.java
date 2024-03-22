package com.ex.hero.order.model.dto.response;

import com.ex.hero.common.annotation.DateFormat;
import com.ex.hero.common.vo.Money;
import com.ex.hero.order.model.Order;
import com.ex.hero.order.model.OrderItem;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class OrderTicketResponse {

    @Schema(description = "티켓명", defaultValue = "일반티켓")
    private final String ticketName;

    @Schema(description = "예매 번호", defaultValue = "1")
    private final String orderNo;

    @Schema(description = "티켓 번호", defaultValue = "1")
    private final String ticketNos;

    @Schema(description = "구매 일시")
    @DateFormat
    private final LocalDateTime paymentAt;

    @Schema(description = "유저이름")
    private final String userName;

    @Schema(description = "금액")
    private final Money orderLinePrice;

    @Schema(description = "구매수량")
    private final Long purchaseQuantity;


    public static OrderTicketResponse of(
            Order order,
            OrderItem orderItem,
            String userName,
            String ticketNos) {
        return OrderTicketResponse.builder()
                .orderNo(order.getOrderNo() + "-" + orderItem.getId())
                .ticketNos(ticketNos)
                .ticketName(orderItem.getItemName())
                .paymentAt(order.getApprovedAt())
                .userName(userName)
                .orderLinePrice(orderItem.getTotalOrderPrice())
                .purchaseQuantity(orderItem.getQuantity())
                .build();
    }
}
