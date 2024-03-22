package com.ex.hero.cart.service;

import com.ex.hero.cart.model.dto.response.CartResponse;
import com.ex.hero.cart.model.mapper.CartMapper;
import com.ex.hero.common.util.UserUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReadCartUseCase {
    private final UserUtils userUtils;
    private final CartMapper cartMapper;
    private final CommonCartService commonCartService;

    public CartResponse execute() {
        Long currentUserId = userUtils.getCurrentMemberId();
        return commonCartService
                .findCartByUserId(currentUserId)
                .map(cartMapper::toCartResponse)
                .orElse(null);
    }
}
