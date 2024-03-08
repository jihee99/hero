package com.ex.hero.common.vo;

import com.ex.hero.events.model.Event;
import com.ex.hero.events.model.EventStatus;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class EventProfileVo {
    private Long eventId;

    private String name;

    private LocalDateTime startAt;

    private LocalDateTime endAt;

    private Long runTime;

    private String placeName;

    private EventStatus status;

    public static EventProfileVo from(Event event) {
        EventBasicVo eventBasicVo = event.toEventBasicVo();
//        EventPlaceVo eventPlaceVo = event.toEventPlaceVo();
        EventDetailVo eventDetailVo = event.toEventDetailVo();

        return EventProfileVo.builder()
                .eventId(event.getId())
                .name(eventBasicVo.getName())
                .startAt(eventBasicVo.getStartAt())
                .endAt(event.getEndAt())
                .runTime(eventBasicVo.getRunTime())
                .status(event.getStatus())
                .build();
    }
}
