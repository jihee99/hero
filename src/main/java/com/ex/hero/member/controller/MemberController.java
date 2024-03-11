package com.ex.hero.member.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ex.hero.common.dto.ApiResponse;
import com.ex.hero.member.model.dto.request.MemberUpdateRequest;
import com.ex.hero.member.service.MemberService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Tag(name="9. 개인정보 관리 API")
@RequiredArgsConstructor
// @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
@RestController
@RequestMapping("/api/v1/member")
public class MemberController {

	private final MemberService memberService;

	@Operation(summary = "회원 정보 조회")
	@GetMapping("/")
	public ApiResponse getMemberInfo(@AuthenticationPrincipal User user){
		return ApiResponse.success(memberService.getMemberInfo(Long.parseLong(user.getUsername())));
	}

	@Operation(summary = "회원 탈퇴")
	@GetMapping("/delete")
	public ApiResponse deleteMember(@AuthenticationPrincipal User user) {
		return ApiResponse.success(memberService.deleteMember(Long.parseLong(user.getUsername())));
	}

	@Operation(summary = "회원 정보 수정")
	@GetMapping("/update")
	public ApiResponse updateMember(@AuthenticationPrincipal User user, @RequestBody MemberUpdateRequest request) {
		return ApiResponse.success(memberService.updateMember(Long.parseLong(user.getUsername()), request));
	}

//	@Operation(summary = "판매자 등록 신청")
//	@GetMapping("/apply-seller")
//	public ApiResponse applySeller(@AuthenticationPrincipal User user, @RequestBody SellerApplyRequest request) {
//		memberService.registerSellerRequest(UUID.fromString(user.getUsername()), request);
//		return ApiResponse.success(null);
//	}

}
