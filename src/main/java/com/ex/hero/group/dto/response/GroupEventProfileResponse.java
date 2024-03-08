package com.ex.hero.group.dto.response;

import com.ex.hero.domains.common.vo.EventProfileVo;
import com.ex.hero.events.model.Event;
import com.ex.hero.group.model.Group;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GroupEventProfileResponse {

    private Long groupId;

    private String groupName;

    @JsonUnwrapped private EventProfileVo eventProfileVo;

    public static GroupEventProfileResponse of(Group group, Event event){
        return GroupEventProfileResponse.builder()
                .groupId(group.getId())
                .groupName(group.getProfile().getName())
                .eventProfileVo(event.toEventProfileVo())
                .build();
    }

}
