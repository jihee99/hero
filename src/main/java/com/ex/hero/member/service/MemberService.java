package com.ex.hero.member.service;

import java.time.LocalDateTime;

import com.ex.hero.member.model.SellerApplyType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ex.hero.member.model.dto.request.MemberUpdateRequest;
import com.ex.hero.member.model.dto.response.MemberDeleteResponse;
import com.ex.hero.member.model.dto.response.MemberInfoResponse;
import com.ex.hero.member.model.dto.response.MemberUpdateResponse;
import com.ex.hero.member.exception.AlreadyAppliedUserException;
import com.ex.hero.member.exception.InvalidMemberException;
import com.ex.hero.member.exception.UserNotFoundException;
import com.ex.hero.member.model.Seller;
import com.ex.hero.member.repository.MemberRepository;
import com.ex.hero.member.repository.SellerRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberService {

	private final MemberRepository memberRepository;
	private final SellerRepository sellerRepository;
	private final PasswordEncoder passwordEncoder;

	@Transactional(readOnly = true)
	public MemberInfoResponse getMemberInfo(Long id) {
		return memberRepository.findById(id)
			.map(MemberInfoResponse::from)
			.orElseThrow(() -> UserNotFoundException.EXCEPTION);
	}

	public MemberDeleteResponse deleteMember(Long id) {
		if (!memberRepository.existsById(id)) return new MemberDeleteResponse(false);
		memberRepository.deleteById(id);
		return new MemberDeleteResponse(true);
	}

	@Transactional
	public MemberUpdateResponse updateMember(Long id, MemberUpdateRequest request) {
		return memberRepository.findById(id)
			.filter(member -> passwordEncoder.matches(request.password(), member.getPassword()))
			.map(member -> {
				member.update(request, passwordEncoder);
				return MemberUpdateResponse.of(true, member);
			})
			.orElseThrow(() -> new InvalidMemberException("아이디 또는 비밀번호가 일치하지 않습니다."));
	}

	public void registerSellerRequest(Long id, MemberUpdateRequest request) {
		// 회원인가?
		memberRepository.findById(id)
			.map(MemberInfoResponse::from)
			.orElseThrow(() -> UserNotFoundException.EXCEPTION);

		// 이미 판매자인가?
		// findById가 존재하면 > AlreadyAppliedUserException
		sellerRepository.findById(id)
			.ifPresentOrElse(
				seller -> {throw AlreadyAppliedUserException.EXCEPTION;},
				() -> {
					//SellerRepository.save

				}
		);

		// 존재하지 않으면 > SellerRepository.save

		log.info("{}", request);
		Seller newSeller = Seller.builder()
				.id(id)
				.applyType(SellerApplyType.APPLY) // 판매자 신청 상태로 설정하거나, 필요에 따라 다른 값을 지정합니다.
				.applyAt(LocalDateTime.now()) // 신청 시간을 현재 시간으로 설정하거나, 필요에 따라 다른 값을 지정합니다.
				.build();
		sellerRepository.save(newSeller);


	}
}
