package com.ex.hero.group.controller;

import com.ex.hero.group.dto.request.InviteGroupRequest;
import com.ex.hero.group.dto.request.UpdateGroupUserRoleRequest;
import com.ex.hero.group.dto.response.GroupDetailResponse;
import com.ex.hero.group.service.InviteGroupUseCase;
import com.ex.hero.group.service.UpdateGroupUserRoleUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Tag(name = "그룹용 API(마스터용)")
@RequestMapping("/api/v1/master")
@RestController
@RequiredArgsConstructor
public class GroupMasterController {

    private final InviteGroupUseCase inviteGroupUseCase;
    private final UpdateGroupUserRoleUseCase updateGroupUserRoleUseCase;

    /* 멤버를 그룹 유저로 초대하는 api */
    @SneakyThrows
    @Operation(summary = "멤버를 그룹 유저로 초대합니다.")
    @PostMapping("/{groupId}/invite")
    public GroupDetailResponse inviteGroup(
            @PathVariable Long groupId, @RequestBody @Valid InviteGroupRequest inviteGroupRequest
    ){
        return inviteGroupUseCase.execute(groupId, inviteGroupRequest);
    }

    /* 그룹 유저의 권한을 변경하는 api (단, 마스터만 가능) */
    @Operation(summary = "그룹 유저의 권한을 변경합니다. 마스터 이상만 가능합니다.")
    @PatchMapping("/admin/{groupId}/role")
    public GroupDetailResponse patchGroupUserRole(
            @PathVariable Long groupId,
            @RequestBody @Valid UpdateGroupUserRoleRequest updateGroupUserRoleRequest
    ) {
        return updateGroupUserRoleUseCase.execute(groupId, updateGroupUserRoleRequest);
    }

    /* 그룹 삭제 api */

}
