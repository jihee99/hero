package com.ex.hero.cart.model.dto.response;

import com.ex.hero.cart.model.Cart;
import com.ex.hero.common.vo.EventProfileVo;
import com.ex.hero.common.vo.Money;
import com.ex.hero.events.model.Event;
import com.ex.hero.ticket.model.TicketItem;
import com.ex.hero.ticket.model.TicketPayType;
import com.ex.hero.ticket.model.TicketType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class CartResponse {

    private final List<CartItemResponse> items;

    @Schema(description = "총 결제금액을 합한 금액입니다", defaultValue = "15000원")
    private final Money totalPrice;

    @Schema(description = "생성한 장바구니의 아이디입니다", defaultValue = "30")
    private final Long cartId;

    @Schema(description = "전체 아이템 수량을 의미합니다", defaultValue = "3")
    private final Long totalQuantity;

    @Schema(description = "결제가 필요한지에 대한 여부를 결정합니다.", defaultValue = "true")
    private final Boolean isNeedPayment;

    @Schema(description = "티켓의 타입. [승인/선착순]")
    private final TicketType approveType;

    @Schema(description = "티켓의 지불 타입. [무료/유료]")
    private final TicketPayType ticketPayType;

    @Schema(description = "이벤트 정보")
    private final EventProfileVo eventProfile;

    public static CartResponse of(
            List<CartItemResponse> cartItemResponses, Cart cart, TicketItem ticketItem, Event event
    ) {
        return CartResponse.builder()
                .items(cartItemResponses)
                .totalPrice(cart.getTotalPrice())
                .cartId(cart.getId())
                .isNeedPayment(cart.isNeedPaid())
                .totalQuantity(cart.getTotalQuantity())
                .approveType(ticketItem.getType())
                .ticketPayType(ticketItem.getPayType())
                .eventProfile(event.toEventProfileVo())
                .build();
    }
}
