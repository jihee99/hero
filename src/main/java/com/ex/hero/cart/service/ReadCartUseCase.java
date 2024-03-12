package com.ex.hero.cart.service;

import com.ex.hero.cart.model.dto.response.CartResponse;
import com.ex.hero.cart.model.mapper.CartMapper;
import com.ex.hero.common.util.MemberUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReadCartUseCase {
    private final MemberUtils memberUtils;
    private final CartMapper cartMapper;
    private final CommonCartService commonCartService;

    public CartResponse execute() {
        Long currentUserId = memberUtils.getCurrentMemberId();
        return commonCartService
                .findCartByUserId(currentUserId)
                .map(cartMapper::toCartResponse)
                .orElse(null);
    }
}
