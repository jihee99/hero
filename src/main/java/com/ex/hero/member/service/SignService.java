package com.ex.hero.member.service;

import com.ex.hero.member.exception.AlreadySignUpUserException;
import com.ex.hero.member.exception.PasswordFormatMismatchException;
import com.ex.hero.member.exception.PasswordIncorrectException;
import com.ex.hero.member.exception.UserNotFoundException;
import com.ex.hero.member.model.dto.response.SignInRequestValidationResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ex.hero.member.model.dto.request.SignInRequest;
import com.ex.hero.member.model.dto.response.SignInResponse;
import com.ex.hero.member.model.dto.request.SignUpRequest;
import com.ex.hero.member.model.dto.response.SignUpResponse;
import com.ex.hero.member.model.Member;
import com.ex.hero.member.repository.MemberRefreshTokenRepository;
import com.ex.hero.member.repository.MemberRepository;
import com.ex.hero.security.jwt.MemberRefreshToken;

import lombok.RequiredArgsConstructor;

@Slf4j
@RequiredArgsConstructor
@Service
public class SignService {


	private final MemberRepository memberRepository;
	private final MemberRefreshTokenRepository memberRefreshTokenRepository;
//	private final TokenProvider tokenProvider;
	private final PasswordEncoder passwordEncoder;    // 추가

	private static final String PASSWORD_REGEX = "^(?=.*[a-zA-Z])(?=.*[0-9])[a-zA-Z0-9]{8,20}$";

	@Transactional
	public SignUpResponse registerMember(SignUpRequest request) {
		Member member = memberRepository.save(new Member(request, passwordEncoder));
		try {
			memberRepository.flush();
		} catch (DataIntegrityViolationException e) {
			// TODO
			// 예외처리
			throw new IllegalArgumentException("이미 사용중인 아이디입니다.");
		}
		return SignUpResponse.from(member);
	}


//	@Transactional
//	public SignInResponse signIn(SignInRequest request) {
//		Member member = memberRepository.findByEmail(request.getEmail())
//				.filter(it -> passwordEncoder.matches(request.getPassword(), it.getPassword()))
//				.orElseThrow(() -> new IllegalArgumentException("아이디 또는 비밀번호가 일치하지 않습니다."));
//		String accessToken = tokenProvider.createAccessToken(String.format("%s:%s", member.getUserId(), member.getAccountRole()));    // token -> accessToken
//		String refreshToken = tokenProvider.createRefreshToken();    // 리프레시 토큰 생성
//		// 리프레시 토큰이 이미 있으면 토큰을 갱신하고 없으면 토큰을 추가
//		memberRefreshTokenRepository.findById(member.getUserId())
//				.ifPresentOrElse(
//						it -> it.updateRefreshToken(refreshToken),
//						() -> memberRefreshTokenRepository.save(new MemberRefreshToken(member, refreshToken))
//				);
//
//		member.login();
//		return new SignInResponse(member.getEmail(), member.getAccountRole(), accessToken, refreshToken);
//	}

	//	@Transactional
//	public SignInResponse signIn(SignInRequest request) {
//		Member member = memberRepository.findByEmail(request.getEmail())
//				.filter(it -> passwordEncoder.matches(request.getPassword(), it.getPassword()))
//				.orElseThrow(() -> new IllegalArgumentException("아이디 또는 비밀번호가 일치하지 않습니다."));
//		String accessToken = tokenProvider.createAccessToken(String.format("%s:%s", member.getUserId(), member.getAccountRole()));    // token -> accessToken
//		String refreshToken = tokenProvider.createRefreshToken();    // 리프레시 토큰 생성
//		// 리프레시 토큰이 이미 있으면 토큰을 갱신하고 없으면 토큰을 추가
//		memberRefreshTokenRepository.findById(member.getUserId())
//				.ifPresentOrElse(
//						it -> it.updateRefreshToken(refreshToken),
//						() -> memberRefreshTokenRepository.save(new MemberRefreshToken(member, refreshToken))
//				);
//
//		member.login();
//		return new SignInResponse(member.getEmail(), member.getAccountRole(), accessToken, refreshToken);
//	}


	@Transactional
	public void signUp(String email, String name, String password) {
		validateMemberNotExist(email);
//		validatePasswordForm(password);

		Member member = Member.from(email, name, passwordEncoder.encode(password));
		log.info("회원가입 univId : {}, name : {}, role : {}", member.getEmail(), member.getName(), member.getAccountRole());
		memberRepository.save(member);
	}

	@Transactional
	public SignInRequestValidationResult validateSignInRequest(SignInRequest signInRequest) {
		Member member = memberRepository.findByEmail(signInRequest.getEmail())
				.orElseThrow(() -> UserNotFoundException.EXCEPTION);

		validateMatchesPassword(signInRequest.getPassword(), member.getPassword());

		return new SignInRequestValidationResult(member.getAccountRole().toString());
	}
	private void validateMemberNotExist(String email) {
		if (memberRepository.findByEmail(email).isPresent()) {
			log.info("[회원가입 실패] 중복 이메일 회원가입 시도 -> univId : " + email);
			throw AlreadySignUpUserException.EXCEPTION;
		}
	}

	private void validatePasswordForm(String password) {
		if (!password.matches(PASSWORD_REGEX)) {
			throw PasswordFormatMismatchException.EXCEPTION;
		}
	}

	private void validateMatchesPassword(String rawPassword, String encodedPassword) {
		if (!passwordEncoder.matches(rawPassword, encodedPassword)) {
			throw PasswordIncorrectException.EXCEPTION;
		}
	}

}