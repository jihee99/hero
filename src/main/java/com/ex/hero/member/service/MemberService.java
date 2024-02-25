package com.ex.hero.member.service;

import java.util.NoSuchElementException;
import java.util.UUID;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ex.hero.member.dto.request.MemberUpdateRequest;
import com.ex.hero.member.dto.response.MemberDeleteResponse;
import com.ex.hero.member.dto.response.MemberInfoResponse;
import com.ex.hero.member.dto.response.MemberUpdateResponse;
import com.ex.hero.member.repository.MemberRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberService {

	private final MemberRepository memberRepository;

	private final PasswordEncoder passwordEncoder;

	@Transactional(readOnly = true)
	public MemberInfoResponse getMemberInfo(UUID id) {
		return memberRepository.findById(id)
			.map(MemberInfoResponse::from)
			.orElseThrow(() -> new NoSuchElementException("존재하지 않는 회원입니다."));
	}

	@Transactional
	public MemberDeleteResponse deleteMember(UUID id) {
		if (!memberRepository.existsById(id)) return new MemberDeleteResponse(false);
		memberRepository.deleteById(id);
		return new MemberDeleteResponse(true);
	}

	@Transactional
	public MemberUpdateResponse updateMember(UUID id, MemberUpdateRequest request) {
		return memberRepository.findById(id)
			.filter(member -> passwordEncoder.matches(request.password(), member.getPassword()))
			.map(member -> {
				member.update(request, passwordEncoder);
				return MemberUpdateResponse.of(true, member);
			})
			.orElseThrow(() -> new NoSuchElementException("아이디 또는 비밀번호가 일치하지 않습니다."));
	}

}
