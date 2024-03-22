package com.ex.hero.cart.service;

import com.ex.hero.cart.model.Cart;
import com.ex.hero.cart.model.dto.request.AddCartRequest;
import com.ex.hero.cart.model.dto.response.CartResponse;
import com.ex.hero.cart.model.mapper.CartMapper;
import com.ex.hero.common.util.UserUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateCartUseCase {

    private final CommonCartService commonCartService;
    private final UserUtils userUtils;
    private final CartMapper cartMapper;

    public CartResponse execute(AddCartRequest addCartRequest){
        final Long userId = userUtils.getCurrentMemberId();

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
