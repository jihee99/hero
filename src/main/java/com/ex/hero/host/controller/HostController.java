package com.ex.hero.host.controller;

import java.util.UUID;

import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ex.hero.host.dto.request.CreateHostRequest;
import com.ex.hero.host.dto.request.InviteHostRequest;
import com.ex.hero.host.dto.response.HostDetailResponse;
import com.ex.hero.host.dto.response.HostResponse;
import com.ex.hero.host.service.CreateHostUseCase;
import com.ex.hero.host.service.HostService;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Tag(name = "호스트용 API")
@RequestMapping("/api/v1/host")
@RestController
@RequiredArgsConstructor
public class HostController {

	private final CreateHostUseCase createHostUseCase;
	private final HostService hostService;

	@Operation(summary = "호스트 간편 생성. 호스트를 생성한 유저는 마스터 호스트가 됩니다.")
	@PostMapping
	public HostResponse createHost(@RequestBody @Valid CreateHostRequest createEventRequest) {
		return createHostUseCase.execute(createEventRequest);
	}

	/* 멤버를 호스트 유저로 초대하는 api */가
	public HostDetailResponse inviteHost(@PathVariable UUID hostId, @RequestBody @Valid InviteHostRequest inviteHostRequest){
		// return inviteHostUseCase.execute(hostId, inviteHostRequest);
		return null;
	}

	/* 초대받은 유저 호스트 가입 api */

	/* 초대받은 유저 호스트 가입 거절 api */

	/* 호스트 유저의 권한을 변경하는 api (단, 마스터만 가능) */

	/* 호스트 정보 업데이트 api (단, 매니저이상부터 가능) */

	/* 해당 호스트가 발급한 티켓 리스트 가져오는 api */

	/* 해당 호스트가 발급한 티켓의 정보 가져오는 api */



}

