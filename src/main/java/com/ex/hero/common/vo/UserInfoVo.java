package com.ex.hero.common.vo;

import com.ex.hero.user.model.User;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class UserInfoVo {

    private final Long userId;

    private final String userName;

    private final String email;

    private final String phoneNumber;

    private final LocalDateTime createdAt;

    public static UserInfoVo from(User user) {
        return UserInfoVo.builder()
                .userId(user.getUserId())
                .userName(user.getName())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .createdAt(user.getCreatedAt())
                .build();
    }
}
