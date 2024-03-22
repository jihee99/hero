package com.ex.hero.auth.service;

import com.ex.hero.user.dto.request.SignUpRequest;
import com.ex.hero.user.dto.response.SignUpResponse;
import com.ex.hero.user.exception.AlreadySignUpUserException;
import com.ex.hero.user.exception.PasswordFormatMismatchException;
import com.ex.hero.user.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ex.hero.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class SignupUseCase {

	private final UserRepository userRepository;
	private final PasswordEncoder encoder;
	private static final String PASSWORD_REGEX = "^(?=.*[a-zA-Z])(?=.*[0-9])[a-zA-Z0-9]{8,20}$";

	public SignUpResponse execute(SignUpRequest request) {
		validateMemberNotExist(request.email());
		validatePasswordForm(request.password());

		User user = userRepository.save(new User(request, encoder));

		return SignUpResponse.from(user);
	}

	private void validateMemberNotExist(String email) {
		if (userRepository.findByEmail(email).isPresent()) {
			log.info("[회원가입 실패] 중복 이메일 회원가입 시도 -> univId : " + email);
			throw AlreadySignUpUserException.EXCEPTION;
		}
	}

	private void validatePasswordForm(String password) {
		if (!password.matches(PASSWORD_REGEX)) {
			throw PasswordFormatMismatchException.EXCEPTION;
		}
	}
}
