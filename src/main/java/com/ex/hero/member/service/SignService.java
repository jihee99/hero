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
import com.ex.hero.member.repository.MemberRefreshTokenRepository;
import com.ex.hero.member.repository.MemberRepository;
import com.ex.hero.security.jwt.MemberRefreshToken;
import com.ex.hero.security.jwt.TokenProvider;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class SignService {


	private final MemberRepository memberRepository;
	private final MemberRefreshTokenRepository memberRefreshTokenRepository;
	private final TokenProvider tokenProvider;
	private final PasswordEncoder passwordEncoder;	// 추가


	@Transactional
	public SignUpResponse registerMember(SignUpRequest request) {
		Member member = memberRepository.save(Member.from(request, passwordEncoder));
		try {
			memberRepository.flush();
		} catch (DataIntegrityViolationException e) {
			// TODO
			// 예외처리
			throw new IllegalArgumentException("이미 사용중인 아이디입니다.");
		}
		return SignUpResponse.from(member);
	}


	@Transactional
	public SignInResponse signIn(SignInRequest request) {
		Member member = memberRepository.findByEmail(request.email())
			.filter(it -> passwordEncoder.matches(request.password(), it.getPassword()))
			.orElseThrow(() -> new IllegalArgumentException("아이디 또는 비밀번호가 일치하지 않습니다."));
		String accessToken = tokenProvider.createAccessToken(String.format("%s:%s", member.getUserId(), member.getAccountRole()));	// token -> accessToken
		String refreshToken = tokenProvider.createRefreshToken();	// 리프레시 토큰 생성
		// 리프레시 토큰이 이미 있으면 토큰을 갱신하고 없으면 토큰을 추가
		memberRefreshTokenRepository.findById(member.getUserId())
			.ifPresentOrElse(
				it -> it.updateRefreshToken(refreshToken),
				() -> memberRefreshTokenRepository.save(new MemberRefreshToken(member, refreshToken))
			);

		member.login();
		return new SignInResponse(member.getEmail(), member.getAccountRole(), accessToken, refreshToken);
	}


}
