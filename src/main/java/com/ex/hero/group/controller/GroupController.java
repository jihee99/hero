package com.ex.hero.group.controller;

import java.util.UUID;

import io.swagger.v3.oas.annotations.tags.Tag;

import lombok.SneakyThrows;
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
@Tag(name = "호스트용 API")
@RequestMapping("/api/v1/host")
@RestController
@RequiredArgsConstructor
public class GroupController {

	private final CreateGroupUseCase createGroupUseCase;
	private final InviteGroupUseCase inviteGroupUseCase;
	private final JoinGroupUseCase joinGroupUseCase;
	private final RejectGroupUseCase rejectGroupUseCase;
	private final UpdateGroupUserRoleUseCase updateGroupUserRoleUseCase;
	private final UpdateGroupProfileUseCase updateGroupProfileUseCase;

	@Operation(summary = "호스트 간편 생성. 호스트를 생성한 유저는 마스터 호스트가 됩니다.")
	@PostMapping
	public GroupResponse createHost(@RequestBody @Valid CreateGroupRequest createEventRequest) {
		return createGroupUseCase.execute(createEventRequest);
	}

	/* 멤버를 호스트 유저로 초대하는 api */
	@SneakyThrows
	@Operation(summary = "멤버를 호스트 유저로 초대합니다.")
	@PostMapping("/host/{groupId}/invite")
	public GroupDetailResponse inviteGroup(
		@PathVariable UUID groupId, @RequestBody @Valid InviteGroupRequest inviteGroupRequest
	){
		return inviteGroupUseCase.execute(groupId, inviteGroupRequest);
	}

	/* 초대받은 유저 호스트 가입 api */
	@Operation(summary = "초대받은 호스트에 가입을 승인힙니다.")
	@PostMapping("/{groupId}/join")
	public GroupDetailResponse joinGroup(@PathVariable UUID groupId) {
		return joinGroupUseCase.execute(groupId);
	}

	/* 초대받은 유저 호스트 가입 거절 api */
	@Operation(summary = "초대받은 호스트에 가입을 거절합니다.")
	@PostMapping("/{hostId}/reject")
	public GroupDetailResponse rejectGroup(@PathVariable UUID hostId) {
		return rejectGroupUseCase.execute(hostId);
	}

	/* 호스트 유저의 권한을 변경하는 api (단, 마스터만 가능) */
	@Operation(summary = "호스트 유저의 권한을 변경합니다. 매니저 이상만 가능합니다.")
	@PatchMapping("/admin/{groupId}/role")
	public GroupDetailResponse patchGroupUserRole(
		@PathVariable UUID groupId,
		@RequestBody @Valid UpdateGroupUserRoleRequest updateGroupUserRoleRequest
	) {
		return updateGroupUserRoleUseCase.execute(groupId, updateGroupUserRoleRequest);
	}

	/* 호스트 정보 업데이트 api (단, 매니저이상부터 가능) */
	@Operation(summary = "호스트 정보를 업데이트 합니다. 매니저 이상부터 가능")
	@PatchMapping("/{groupId}/profile")
	public GroupDetailResponse patchHostById(
		@PathVariable UUID groupId, @RequestBody @Valid UpdateGroupRequest updateGroupRequest
	){
		return updateGroupProfileUseCase.execute(groupId, updateGroupRequest);
	}

	/* 해당 호스트가 발급한 티켓 리스트 가져오는 api */

	/* 해당 호스트가 발급한 티켓의 정보 가져오는 api */



}

