package com.ex.hero.member.controller;

import java.util.UUID;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ex.hero.common.dto.ApiResponse;
import com.ex.hero.common.dto.ResultMap;
import com.ex.hero.member.dto.request.MemberUpdateRequest;
import com.ex.hero.member.service.MemberService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Tag(name="로그인 후 사용자가 접근할 수 있는 API")
@RequiredArgsConstructor
// @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
@RestController
@RequestMapping("/api/v1/member")
public class MemberController {

	private final MemberService memberService;

	@Operation(summary = "회원 정보 조회")
	@GetMapping("/")
	public ApiResponse getMemberInfo(@AuthenticationPrincipal User user){
		return ApiResponse.success(memberService.getMemberInfo(UUID.fromString(user.getUsername())));
	}

	@Operation(summary = "회원 탈퇴")
	@GetMapping("/delete")
	public ApiResponse deleteMember(@AuthenticationPrincipal User user) {
		return ApiResponse.success(memberService.deleteMember(UUID.fromString(user.getUsername())));
	}

	@Operation(summary = "회원 정보 수정")
	@GetMapping("/update")
	public ApiResponse updateMember(@AuthenticationPrincipal User user, @RequestBody MemberUpdateRequest request) {
		return ApiResponse.success(memberService.updateMember(UUID.fromString(user.getUsername()), request));
	}

	@GetMapping("/apply-seller")
	public ApiResponse applySeller(@AuthenticationPrincipal User user, @RequestBody MemberUpdateRequest request) {
		// memberService.appleySeller(UUID.fromString(user.getUsername()), request);
		return ApiResponse.success(null);
	}
}
