package com.ex.hero.host.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ex.hero.host.dto.request.CreateHostRequest;
import com.ex.hero.host.dto.response.HostResponse;
import com.ex.hero.host.service.CreateHostUseCase;
import com.ex.hero.host.service.HostService;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping("/api/v1/seller")
@RestController
@RequiredArgsConstructor
public class HostController {

	private final CreateHostUseCase createHostUseCase;
	private final HostService hostService;

	@Operation(summary = "호스트 간편 생성. 호스트를 생성한 유저 자신은 마스터 호스트가 됩니다.")
	@PostMapping
	public HostResponse createHost(@RequestBody @Valid CreateHostRequest createEventRequest) {
		return createHostUseCase.execute(createEventRequest);
	}


}

