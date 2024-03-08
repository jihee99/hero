package com.ex.hero.domains.common.vo;

import com.ex.hero.events.model.Event;
import com.ex.hero.events.model.EventDetail;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class EventDetailVo {
    // (마크다운) 전시 상세 내용
    private String content;

    public static EventDetailVo from(Event event) {
        EventDetail eventDetail = event.getEventDetail();
        if (eventDetail == null) {
            return EventDetailVo.builder().build();
        }
        return EventDetailVo.builder()
                .content(eventDetail.getContent())
                .build();
    }
}
