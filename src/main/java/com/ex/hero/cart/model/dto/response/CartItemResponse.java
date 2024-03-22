package com.ex.hero.cart.model.dto.response;

import com.ex.hero.cart.model.CartItem;
import com.ex.hero.common.vo.Money;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CartItemResponse {

    @Schema(description = "아이템의 이름입니다.", defaultValue = "일반티켓 2매")
    private String name;

    @Schema(description = "아이템 금액 입니다..", defaultValue = "3000원")
    private Money itemPrice;

    @Schema(description = "카트 아이템의 총 가격입니다. 아이템 가격 * 수량", defaultValue = "4000원")
    private Money cartPrice;

    @Schema(description = "담은 상품의 개수입니다.", defaultValue = "1")
    private Long packedQuantity;

    public static CartItemResponse of(
            CartItem cartItem, String itemName
    ) {
        return CartItemResponse.builder()
                .name(itemName)
                .cartPrice(cartItem.getTotalCartPrice())
                .itemPrice(cartItem.getItemPrice())
                .packedQuantity(cartItem.getQuantity())
                .build();
    }
}
