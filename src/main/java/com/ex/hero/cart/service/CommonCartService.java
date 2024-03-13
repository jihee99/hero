package com.ex.hero.cart.service;

import com.ex.hero.cart.exception.CartNotFoundException;
import com.ex.hero.cart.model.Cart;
import com.ex.hero.cart.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommonCartService {
    private final CartRepository cartRepository;

    public Cart save(Cart cart) {
        return cartRepository.save(cart);
    }

    public Cart queryCart(Long cartId) {
        return cartRepository.findById(cartId).orElseThrow(() -> CartNotFoundException.EXCEPTION);
    }

    public Cart queryCart(Long cartId, Long userId) {
        return cartRepository.findByIdAndUserId(cartId, userId).orElseThrow(() -> CartNotFoundException.EXCEPTION);
    }

    public Optional<Cart> findCartByUserId(Long userId) {
        return cartRepository.findByUserId(userId);
    }

    public Cart find(Long cartId) {
        return cartRepository.findById(cartId).orElseThrow(() -> CartNotFoundException.EXCEPTION);
    }

    @Transactional
    public void deleteByUserId(Long userId) {
        cartRepository.deleteByUserId(userId);
    }

}
