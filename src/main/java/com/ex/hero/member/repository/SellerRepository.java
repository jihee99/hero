package com.ex.hero.member.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ex.hero.member.model.Seller;

public interface SellerRepository extends JpaRepository <Seller, Long> {
}
