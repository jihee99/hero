package com.ex.hero.cart.service;

import com.ex.hero.cart.model.Cart;
import com.ex.hero.cart.model.dto.request.AddCartRequest;
import com.ex.hero.cart.model.dto.response.CartResponse;
import com.ex.hero.cart.model.mapper.CartMapper;
import com.ex.hero.common.util.MemberUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateCartUseCase {

    private CommonCartService commonCartService;
    private final MemberUtils memberUtils;
    private final CartMapper cartMapper;

    public CartResponse execute(AddCartRequest addCartRequest){
        final Long userId = memberUtils.getCurrentMemberId();

        Cart cart = cartMapper.toEntity(addCartRequest, userId);
        Long cartId = createCart(cart, userId);

        return cartMapper.toCartResponse(cartId);
    }

    private Long createCart(Cart cart, Long userId) {
        commonCartService.deleteByUserId(userId);
        Cart savedCart = commonCartService.save(cart);
        return savedCart.getId();
    }

}
