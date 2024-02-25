package com.ex.hero.member.controller;

import java.util.UUID;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ex.hero.common.dto.ApiResponse;
import com.ex.hero.member.dto.request.MemberUpdateRequest;
import com.ex.hero.member.service.MemberService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name="로그인 후 사용자가 접근할 수 있는 API")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/member")
public class MemberController {

	private final MemberService memberService;

	@Operation(summary = "회원 정보 조회")
	@GetMapping("/")
	public ApiResponse getMemberInfo(String id){
		return ApiResponse.success(memberService.getMemberInfo(UUID.fromString(id)));
	}

	@Operation(summary = "회원 탈퇴")
	@DeleteMapping
	public ApiResponse deleteMember(String id) {
		return ApiResponse.success(memberService.deleteMember(UUID.fromString(id)));
	}

	@Operation(summary = "회원 정보 수정")
	@PutMapping
	public ApiResponse updateMember(String id, @RequestBody MemberUpdateRequest request) {
		return ApiResponse.success(memberService.updateMember(UUID.fromString(id), request));
	}
}
