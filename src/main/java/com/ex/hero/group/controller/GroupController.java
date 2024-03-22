package com.ex.hero.group.controller;

import com.ex.hero.common.vo.UserProfileVo;
import com.ex.hero.group.model.dto.request.CreateGroupRequest;
import com.ex.hero.group.model.dto.request.UpdateGroupRequest;
import com.ex.hero.group.model.dto.request.response.GroupDetailResponse;
import com.ex.hero.group.model.dto.request.response.GroupEventProfileResponse;
import com.ex.hero.group.model.dto.request.response.GroupProfileResponse;
import com.ex.hero.group.model.dto.request.response.GroupResponse;
import com.ex.hero.group.service.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Tag(name = "2. 그룹 관리 API")
@RequestMapping("/api/v1/group")
@RestController
@RequiredArgsConstructor
public class GroupController {

	private final CreateGroupUseCase createGroupUseCase;
	private final JoinGroupUseCase joinGroupUseCase;
	private final RejectGroupUseCase rejectGroupUseCase;
	private final UpdateGroupProfileUseCase updateGroupProfileUseCase;
	private final ReadInviteUsersUseCase readInviteUsersUseCase;
	private final ReadGroupEventUseCase readGroupEventsUseCase;
	private final ReadGroupsUseCase readGroupsUseCase;
	private final ReadGroupUseCase readGroupUseCase;

	@Operation(summary = "그룹 간편 생성. 그룹을 생성한 유저는 마스터 사용자가 됩니다.")
	@PostMapping
	public GroupResponse createGroup(@RequestBody @Valid CreateGroupRequest createEventRequest) {
		return createGroupUseCase.execute(createEventRequest);
	}

	@Operation(summary = "내가 속한 그룹 리스트를 가져옵니다.")
	@GetMapping
	public List<GroupProfileResponse> getAllHosts() {
		return readGroupsUseCase.execute();
	}

	@Operation(summary = "고유 아이디에 해당하는 그룹 정보를 가져옵니다.")
	@GetMapping("/{groupId}")
	public GroupDetailResponse getHostById(@PathVariable Long groupId) {
		return readGroupUseCase.execute(groupId);
	}

	@Operation(summary = "해당 그룹에 가입하지 않은 유저를 이메일로 검색합니다.")
	@GetMapping("/{groupId}/invite/users")
	public UserProfileVo getInviteUserListByEmail(
			@PathVariable Long groupId, @RequestParam(value = "email") @Email String email) {
		return readInviteUsersUseCase.execute(groupId, email);
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

	// TODO 이벤트 리스트 조회 서비스 생성하기
	@Operation(summary = "해당 그룹에서 관리중인 이벤트 리스트를 가져옵니다.")
	@GetMapping("/{groupId}/events")
	public List<GroupEventProfileResponse> getHostEventsById (@PathVariable Long groupId) {
		return readGroupEventsUseCase.execute(groupId);
	}


}

