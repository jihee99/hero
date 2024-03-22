package com.ex.hero.ticket.model;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum IssuedTicketsStage {
    // 대기
    APPROVE_WAITING("APPROVE_WAITING", "승인대기"),
    // 입장완료
    AFTER_ENTRANCE("AFTER_ENTRANCE", "입장완료"),
    // 관람예정
    BEFORE_ENTRANCE("BEFORE_ENTRANCE", "미입장"),
    // 취소됨
    CANCELED("CANCELED", "취소"),

    PASSED_EVENT("PASSED_EVENT", "만료");
    private final String value;

    @JsonValue
    private final String kr;
}
