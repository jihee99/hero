package com.ex.hero.common.vo;

import com.ex.hero.member.model.Member;
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

    public static IssuedTicketUserInfoVo from(Member member) {
        return IssuedTicketUserInfoVo.builder()
                .userId(member.getUserId())
                .userName(member.getName())
                .email(member.getEmail())
                .build();
    }

}

