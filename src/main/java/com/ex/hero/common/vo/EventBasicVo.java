package com.ex.hero.common.vo;

import com.ex.hero.common.annotation.DateFormat;
import com.ex.hero.events.model.Event;
import com.ex.hero.events.model.EventBasic;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class EventBasicVo {

    private String name;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy.MM.dd HH:mm", timezone = "Asia/Seoul")
    private LocalDateTime startAt;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy.MM.dd HH:mm", timezone = "Asia/Seoul")
    private LocalDateTime endAt;
    private Long runTime;

    public static EventBasicVo from(Event event) {
        EventBasic eventBasic = event.getEventBasic();
        if (eventBasic == null) {
            return EventBasicVo.builder().build();
        }
        return EventBasicVo.builder()
                .name(eventBasic.getName())
                .startAt(eventBasic.getStartAt())
                .endAt(event.getEndAt())
                .runTime(eventBasic.getRunTime())
                .build();
    }
}
