package com.ex.hero.common.vo;

import com.ex.hero.events.model.Event;
import com.ex.hero.events.model.EventBasic;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class EventBasicVo {

    private String name;
    private LocalDateTime startAt;
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
