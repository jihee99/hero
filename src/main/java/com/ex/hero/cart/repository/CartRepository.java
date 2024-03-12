package com.ex.hero.cart.repository;

import com.ex.hero.cart.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {

    Optional<Cart> findByIdAndUserId(Long id, Long userId);
    Optional<Cart> findByUserId(Long userId);
    Optional<Cart> deleteByUserId(Long userId);
}
