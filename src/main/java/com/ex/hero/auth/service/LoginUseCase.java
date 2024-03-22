package com.ex.hero.auth.service;

import com.ex.hero.security.jwt.JwtTokenProvider;
import com.ex.hero.user.dto.request.SignInRequest;
import com.ex.hero.user.dto.response.SignInResponse;
import com.ex.hero.user.model.User;
import com.ex.hero.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LoginUseCase {

    private final UserRepository userRepository;
    private final PasswordEncoder encoder;
    private final JwtTokenProvider jwtTokenProvider;

    @Transactional(readOnly = true)
    public SignInResponse execute(SignInRequest request) {
        User user = userRepository.findByEmail(request.email())
                .filter(it -> encoder.matches(request.password(), it.getPassword()))
                .orElseThrow(() -> new IllegalArgumentException("아이디 또는 비밀번호가 일치하지 않습니다."));

        user.login();

        String newAccessToken = jwtTokenProvider.generateAccessToken(user.getUserId(), user.getAccountRole().getValue());
        String newRefreshToken = jwtTokenProvider.generateRefreshToken(user.getUserId());

        return SignInResponse.builder()
                .userId(user.getUserId())
                .name(user.getName())
                .role(user.getAccountRole())
                .accessToken(newAccessToken)
                .refreshToken(newRefreshToken)
                .build();
    }

}
