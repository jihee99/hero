package com.ex.hero.member.controller;

import com.ex.hero.common.dto.ApiResponse;
import com.ex.hero.member.model.Member;
import com.ex.hero.member.model.dto.request.SignInRequest;
import com.ex.hero.member.model.dto.request.SignUpRequest;
import com.ex.hero.member.service.SignService;
import com.ex.hero.security2.dto.ReissueTokenRequest;
import com.ex.hero.security2.dto.Token;
import com.ex.hero.security2.jwt.JwtTokenProvider22;
import com.ex.hero.security2.principal.AuthDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Tag(name = "0. 회원 가입 및 로그인 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/v3")
public class AuthController {

    private final SignService signService;
    private final JwtTokenProvider22 jwtTokenProvider;


    @Operation(summary = "회원 가입")
    @PostMapping("/sign-up")
    public ApiResponse signUp(@RequestBody SignUpRequest request){
        return ApiResponse.success(signService.registerMember(request));
    }

    @Operation(summary = "로그인")
    @PostMapping("/login")
    public Token login(@RequestBody SignInRequest signInRequest, HttpServletResponse response) {
        // 로그인 시 access token, refresh token 이 생성되어 response header 에 담아 보낸다.
        // rtk 는 레디스에 저장한다. 추후 atk 만료시 rtk를 이용해 atk 재발급 하기 위함
        Member member = signService.login(signInRequest);
        Token token = jwtTokenProvider.createTokenByLogin(member.getEmail(), member.getAccountRole());//atk, rtk 생성

        response.addHeader(AUTHORIZATION, token.getAccessToken());// 헤더에 에세스 토큰만 싣기
        return token;
    }

    @Operation(summary = "로그아웃")
    @DeleteMapping("/logout")
    public ResponseEntity logout(@AuthenticationPrincipal AuthDetails authDetails, HttpServletRequest request) {
        String accessToken = jwtTokenProvider.resolveToken(request);
        return signService.logout(accessToken,authDetails.getUsername());
    }


    /* access token 재발급 */
    // 매 API 호출 시 시큐리티 필터를 통해 인증인가를 받게 된다.
    // 이때 만료된 토큰인지 검증하고 만료시 만료된토큰임을 에러메세지로 보낸다.
    // 그럼 클라이언트에서 에러메세지를 확인 후 이 api(atk 재발급 ) 을 요청 하게 된다.
    @PostMapping("/reissue-token")
    public Token reissueToken(@AuthenticationPrincipal AuthDetails authDetails, @RequestBody ReissueTokenRequest tokenRequest){
        Member member = authDetails.getMember();
        return jwtTokenProvider.reissueAtk(member.getEmail(),member.getAccountRole(), tokenRequest.getRefreshToken());
    }

}
