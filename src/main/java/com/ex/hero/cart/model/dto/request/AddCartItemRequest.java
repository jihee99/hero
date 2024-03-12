package com.ex.hero.cart.model.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class AddCartItemRequest {

    @Schema(description = "주문할 아이템 아이디", defaultValue = "1")
    private Long itemId;

    @Schema(description = "상품 수량", defaultValue = "1")
    private Long quantity;

}
