package com.ex.hero.group.dto.response;

import com.ex.hero.group.model.Group;
import com.ex.hero.group.model.GroupUser;
import com.ex.hero.group.model.GroupUserRole;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GroupProfileResponse {
    @Schema(description = "그룹 고유 아이디")
    private final Long hostId;

    @Schema(description = "그룹 이름")
    private final String name;

    @Schema(description = "그룹 한줄 소개")
    private final String introduce;

    @Schema(description = "속한 그룹에서의 역할")
    private GroupUserRole role;

    @Schema(description = "이 그룹의 마스터인지 여부")
    private final Boolean isMaster;

    @Schema(description = "이 그룹의 초대를 수락했는지 여부")
    private final Boolean active;

    public static GroupProfileResponse of(Group group, Long userId) {
        GroupUser groupUser = group.getGroupUserByUserId(userId);
        return GroupProfileResponse.builder()
                .hostId(group.getId())
                .name(group.getProfile().getName())
                .introduce(group.getProfile().getIntroduce())
//                .profileImage(group.getProfile().getProfileImage())
                .role(groupUser.getRole())
                .isMaster(group.getMasterUserId().equals(userId))
                .active(groupUser.getActive())
                .build();
    }
}
