package com.ex.hero.common.vo;

import com.ex.hero.user.model.User;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserProfileVo {

    private final Long userId;
    private final String userName;
    private final String email;

    public static UserProfileVo from(User user) {
        return UserProfileVo.builder()
                .userId(user.getUserId())
                .userName(user.getName())
                .email(user.getEmail())
                .build();
    }
}
