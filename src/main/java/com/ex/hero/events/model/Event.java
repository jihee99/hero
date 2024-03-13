package com.ex.hero.events.model;

import com.ex.hero.common.exception.HeroException;
import com.ex.hero.common.model.BaseTimeEntity;
import com.ex.hero.common.vo.EventBasicVo;
import com.ex.hero.common.vo.EventDetailVo;
import com.ex.hero.common.vo.EventInfoVo;
import com.ex.hero.common.vo.EventProfileVo;
import com.ex.hero.events.exception.*;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
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

    public Boolean hasEventInfo() {
        return this.eventBasic != null && this.eventBasic.isUpdated() && this.eventDetail != null;
    }


    public Boolean isPreparing() { return this.status == EventStatus.PREPARING; }

    public Boolean isClosed() {
        return this.status == EventStatus.CLOSED;
    }

    public void setEventBasic(EventBasic eventBasic){
        this.validateOpenStatus();
        this.eventBasic = eventBasic;
    }

    public void setEventDetail(EventDetail eventDetail) {
        this.eventDetail = eventDetail;
    }

    @Builder
    public Event(Long groupId, String name, LocalDateTime startAt, Long runTime) {
        this.groupId = groupId;
        this.eventBasic = EventBasic.builder().name(name).startAt(startAt).runTime(runTime).build();
    }

    public void validateOpenStatus() {
        if (status == EventStatus.OPEN) throw CannotModifyOpenEventException.EXCEPTION;
    }

    public void validateNotOpenStatus() {
        if (status != EventStatus.OPEN) throw EventNotOpenException.EXCEPTION;
    }

    public void validateTicketingTime() {
        if (!isTimeBeforeStartAt()) throw AlreadyOpenStatusException.EXCEPTION;
    }

    public boolean isTimeBeforeStartAt() {
        return LocalDateTime.now().isBefore(getStartAt());
    }

    public void validateStartAt() {
        if (this.getStartAt().isBefore(LocalDateTime.now()))
            throw EventOpenTimeExpiredException.EXCEPTION;
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

    public void open() {
        validateStartAt();
        updateStatus(EventStatus.OPEN, AlreadyOpenStatusException.EXCEPTION);
    }

    public void close() {
        updateStatus(EventStatus.CLOSED, AlreadyCloseStatusException.EXCEPTION);
    }

    public void prepare() {
        updateStatus(EventStatus.PREPARING, AlreadyPreparingStatusException.EXCEPTION);
    }


    private void updateStatus(EventStatus status, HeroException exception) {
        if (this.status == status) throw exception;
        this.status = status;
    }

    public void deleteSoft() {
        // 오픈된 이벤트는 삭제 불가
        if (this.status == EventStatus.OPEN) throw CannotDeleteByOpenEventException.EXCEPTION;
        if (this.status == EventStatus.DELETED) throw AlreadyDeletedStatusException.EXCEPTION;
        this.status = EventStatus.DELETED;
    }
}
