package com.ex.hero.member.vo;

import com.ex.hero.member.model.Member;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MemberProfileVo {

    private final Long userId;
    private final String userName;
    private final String email;

    public static MemberProfileVo from(Member member) {
        return MemberProfileVo.builder()
                .userId(member.getUserId())
                .userName(member.getName())
                .email(member.getEmail())
                .build();
    }
}
