package com.ex.hero.common.vo;

import com.ex.hero.user.model.User;
import jakarta.persistence.Embeddable;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@NoArgsConstructor
public class IssuedTicketUserInfoVo {

    private Long userId;

    private String userName;

    private String email;

    @Builder
    public IssuedTicketUserInfoVo(
            Long userId, String userName, String email) {
        this.userId = userId;
        this.userName = userName;
        this.email = email;
    }

    public static IssuedTicketUserInfoVo from(User user) {
        return IssuedTicketUserInfoVo.builder()
                .userId(user.getUserId())
                .userName(user.getName())
                .email(user.getEmail())
                .build();
    }

}

