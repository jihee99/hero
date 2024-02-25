package com.ex.hero.member.service;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ex.hero.member.dto.request.SignInRequest;
import com.ex.hero.member.dto.response.SignInResponse;
import com.ex.hero.member.dto.request.SignUpRequest;
import com.ex.hero.member.dto.response.SignUpResponse;
import com.ex.hero.member.model.Member;
import com.ex.hero.member.repository.MemberRepository;
import com.ex.hero.security.jwt.TokenProvider;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class SignService {


	private final MemberRepository memberRepository;
	private final TokenProvider tokenProvider;
	private final PasswordEncoder passwordEncoder;	// 추가


	@Transactional
	public SignUpResponse registerMember(SignUpRequest request) {
		Member member = memberRepository.save(Member.from(request, passwordEncoder));
		try {
			memberRepository.flush();
		} catch (DataIntegrityViolationException e) {
			throw new IllegalArgumentException("이미 사용중인 아이디입니다.");
		}
		return SignUpResponse.from(member);
	}


	@Transactional(readOnly = true)
	public SignInResponse signIn(SignInRequest request) {
		Member member = memberRepository.findByAccount(request.account())
			.filter(it -> passwordEncoder.matches(request.password(), it.getPassword()))
			.orElseThrow(() -> new IllegalArgumentException("아이디 또는 비밀번호가 일치하지 않습니다."));
		// return new SignInResponse(member.getName(), member.getRole());
		String token = tokenProvider.createToken(String.format("%s:%s",member.getId(),member.getRole()));
		System.out.println(token);
		return new SignInResponse(member.getName(), member.getRole(), token);
	}


}
