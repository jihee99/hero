package com.ex.hero.events.model;

import com.fasterxml.jackson.annotation.JacksonAnnotationsInside;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EventStatus {
    PREPARING("PREPARING", "준비중"),
    OPEN("OPEN", "진행중"),
    CLOSED("CLOSED", "종료된전시"),
    DELETED("DELETED", "삭제된전시");

    private final String name;
    @JsonValue
    private final String value;
}
