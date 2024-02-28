package com.ex.hero;

import java.time.LocalDateTime;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.ex.hero.member.model.Member;
import com.ex.hero.member.model.MemberType;
import com.ex.hero.member.model.Seller;
import com.ex.hero.member.model.SellerApplyType;
import com.ex.hero.member.repository.MemberRepository;
import com.ex.hero.member.repository.SellerRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class SellerInitializer implements ApplicationRunner {
	private final MemberRepository memberRepository;
	private final SellerRepository sellerRepository;

	private final PasswordEncoder passwordEncoder;

	@Override
	public void run(ApplicationArguments args) {
		memberRepository.save(Member.builder()
			.account("seller")
			.password(passwordEncoder.encode("1234"))
			.name("판매자")
			.role(MemberType.SELLER)
			.createdAt(LocalDateTime.now())
			.build());
	}

}
