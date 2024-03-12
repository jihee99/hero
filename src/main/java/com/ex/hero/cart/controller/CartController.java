package com.ex.hero.cart.controller;

import com.ex.hero.cart.model.dto.request.AddCartRequest;
import com.ex.hero.cart.model.dto.response.CartResponse;
import com.ex.hero.cart.service.CreateCartUseCase;
import com.ex.hero.cart.service.ReadCartUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(name = "5. 장바구니 관리 api")
@RestController
@RequestMapping("/api/v1/carts")
@RequiredArgsConstructor
public class CartController {

    private final CreateCartUseCase createCartUseCase;
    private final ReadCartUseCase readCartUseCase;

    @Operation(summary = "상품을 장바구니에 담습니다.")
    @PostMapping
    public CartResponse createCartLines(@RequestBody @Valid AddCartRequest addCartRequest) {
        return createCartUseCase.execute(addCartRequest);
    }

    @Operation(summary = "사용자가 최근에 만들었던 장바구니를 불러옵니다.")
    @GetMapping("/recent")
    public CartResponse getRecentMyCart() {
        return readCartUseCase.execute();
    }

}
