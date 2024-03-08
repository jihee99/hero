package com.ex.hero.domains.common.vo;

import com.ex.hero.events.model.Event;
import com.ex.hero.events.model.EventStatus;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class EventInfoVo {

    private final String eventName;

    @JsonUnwrapped
    private final EventDetailVo eventDetailVo;

    private final LocalDateTime startAt;

    private final LocalDateTime endAt;

    private final EventStatus eventStatus;

    public static EventInfoVo from(Event event) {
        return EventInfoVo.builder()
                .eventName(EventBasicVo.from(event).getName())
                .eventDetailVo(EventDetailVo.from(event))
                .startAt(event.getStartAt())
                .endAt(event.getEndAt())
                .eventStatus(event.getStatus())
                .build();
    }
}
