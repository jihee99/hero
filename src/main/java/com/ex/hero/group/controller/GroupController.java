package com.ex.hero.group.controller;

import java.util.UUID;

import io.swagger.v3.oas.annotations.tags.Tag;

import lombok.SneakyThrows;

import org.springdoc.api.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ex.hero.group.dto.request.CreateGroupRequest;
import com.ex.hero.group.dto.request.InviteGroupRequest;
import com.ex.hero.group.dto.request.UpdateGroupRequest;
import com.ex.hero.group.dto.request.UpdateGroupUserRoleRequest;
import com.ex.hero.group.dto.response.GroupDetailResponse;
import com.ex.hero.group.dto.response.GroupResponse;
import com.ex.hero.group.service.CreateGroupUseCase;
import com.ex.hero.group.service.InviteGroupUseCase;
import com.ex.hero.group.service.JoinGroupUseCase;
import com.ex.hero.group.service.RejectGroupUseCase;
import com.ex.hero.group.service.UpdateGroupProfileUseCase;
import com.ex.hero.group.service.UpdateGroupUserRoleUseCase;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Tag(name = "그룹용 API")
@RequestMapping("/api/v1/group")
@RestController
@RequiredArgsConstructor
public class GroupController {

	private final CreateGroupUseCase createGroupUseCase;
	private final JoinGroupUseCase joinGroupUseCase;
	private final RejectGroupUseCase rejectGroupUseCase;
	private final UpdateGroupProfileUseCase updateGroupProfileUseCase;

	@Operation(summary = "그룹 간편 생성. 그룹을 생성한 유저는 마스터 사용자가 됩니다.")
	@PostMapping
	public GroupResponse createGroup(@RequestBody @Valid CreateGroupRequest createEventRequest) {
		return createGroupUseCase.execute(createEventRequest);
	}

	/* 해당 그룹이 발급한 티켓 리스트 가져오는 api */
	@Operation(summary = "해당 그룹이 관리중인 티켓 리스트를 가져옵니다.")
	@GetMapping("/{groupId}/events")
	public PageResponse<HostEventProfileResponse> getHostEventsById(
		@PathVariable Long groupId,
		@ParameterObject @PageableDefault(size = 10) Pageable pageable) {
		return readHostEventsUseCase.execute(groupId, pageable);
	}

	/* 초대받은 유저 그룹 가입 api */
	@Operation(summary = "초대받은 그룹에 가입을 승인힙니다.")
	@PostMapping("/{groupId}/join")
	public GroupDetailResponse joinGroup(@PathVariable Long groupId) {
		return joinGroupUseCase.execute(groupId);
	}

	/* 초대받은 유저 그룹 가입 거절 api */
	@Operation(summary = "초대받은 그룹에 가입을 거절합니다.")
	@PostMapping("/{groupId}/reject")
	public GroupDetailResponse rejectGroup(@PathVariable Long groupId) {
		return rejectGroupUseCase.execute(groupId);
	}


	/* 그룹 정보 업데이트 api (단, 매니저이상부터 가능) */
	@Operation(summary = "그룹 정보를 업데이트 합니다. 매니저 이상부터 가능")
	@PostMapping("/{groupId}/profile")
	public GroupDetailResponse patchGroupById(
		@PathVariable Long groupId, @RequestBody @Valid UpdateGroupRequest updateGroupRequest
	) {
		return updateGroupProfileUseCase.execute(groupId, updateGroupRequest);
	}

	/* 해당 그룹에서 발급한 티켓 리스트 가져오는 api */



	/* 해당 그룹에서 발급한 티켓의 정보 가져오는 api */



}

