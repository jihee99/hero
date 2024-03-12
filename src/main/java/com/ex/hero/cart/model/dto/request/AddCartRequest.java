package com.ex.hero.cart.model.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.Getter;

import java.util.List;

@Getter
public class AddCartRequest {

    @Schema(description = "주문할 상품 수량")
    @Size(min = 1)
    private List<AddCartItemRequest> items;

}
