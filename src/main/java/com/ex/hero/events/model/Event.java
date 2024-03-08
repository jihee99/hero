package com.ex.hero.events.model;

import com.ex.hero.common.model.BaseTimeEntity;
import com.ex.hero.common.vo.EventBasicVo;
import com.ex.hero.common.vo.EventDetailVo;
import com.ex.hero.common.vo.EventInfoVo;
import com.ex.hero.common.vo.EventProfileVo;
import com.ex.hero.events.exception.CannotModifyOpenEventException;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Where;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Where(clause = "status != 'DELETED'")
@Entity(name = "tbl_event")
public class Event extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "event_id")
    private Long id;

    // 호스트 정보
    private Long groupId;

    @Embedded private EventBasic eventBasic;

//    TODO 고민중..
//    @Embedded private EventPlace eventPlace;

    @Embedded private EventDetail eventDetail;

    // 이벤트 상태
    @Enumerated(EnumType.STRING)
    private EventStatus status = EventStatus.PREPARING;

    public LocalDateTime getStartAt() {
        if (this.eventBasic == null) return null;
        return this.getEventBasic().getStartAt();
    }

    public LocalDateTime getEndAt() {
        if (this.eventBasic == null) return null;
        return this.getEventBasic().endAt();
    }

    public Boolean hasEventBasic() {
        return this.eventBasic != null && this.eventBasic.isUpdated();
    }

    public Boolean hasEventDetail(){
        return this.eventDetail != null && this.eventDetail.isUpdated();
    }

    public Boolean isPreparing() { return this.status == EventStatus.PREPARING; }

    public Boolean isClosed() {
        return this.status == EventStatus.CLOSED;
    }

    public void setEventBasic(EventBasic eventBasic){
        this.validateOpenStatus();
        this.eventBasic = eventBasic;
    }

    public void validateOpenStatus() {
        if (status == EventStatus.OPEN) throw CannotModifyOpenEventException.EXCEPTION;
    }



    public EventInfoVo toEventInfoVo() {
        return EventInfoVo.from(this);
    }

    public EventDetailVo toEventDetailVo() {
        return EventDetailVo.from(this);
    }

    public EventBasicVo toEventBasicVo() {
        return EventBasicVo.from(this);
    }
    public EventProfileVo toEventProfileVo() { return EventProfileVo.from(this); }

}