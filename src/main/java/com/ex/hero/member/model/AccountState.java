package com.ex.hero.member.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum AccountState {
    NORMAL("NORMAL"),
    // 탈퇴한유저
    DELETED("DELETED"),
    // 영구정지
    FORBIDDEN("FORBIDDEN");

    private String value;
}
