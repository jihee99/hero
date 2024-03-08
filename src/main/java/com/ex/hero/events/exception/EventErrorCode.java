package com.ex.hero.events.exception;

import static com.ex.hero.common.exception.HeroStatic.BAD_REQUEST;
import static com.ex.hero.common.exception.HeroStatic.NOT_FOUND;

import java.lang.reflect.Field;
import java.util.Objects;

import com.ex.hero.common.annotation.ExplainError;
import com.ex.hero.common.dto.ErrorReason;
import com.ex.hero.common.exception.BaseErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EventErrorCode implements BaseErrorCode {
    EVENT_NOT_FOUND(NOT_FOUND, "Event_404_1", "이벤트를 찾을 수 없습니다."),

    HOST_NOT_AUTH_EVENT(BAD_REQUEST, "Event_400_1", "Group Not Auth Event."),
    EVENT_CANNOT_END_BEFORE_START(BAD_REQUEST, "Event_400_2", "시작 시각은 종료 시각보다 빨라야 합니다."),
    EVENT_URL_NAME_ALREADY_EXIST(BAD_REQUEST, "Event_400_3", "중복된 URL 표시 이름입니다."),
    CANNOT_MODIFY_OPEN_EVENT(BAD_REQUEST, "Event_400_4", "오픈된 전시 정보는 수정할 수 없습니다."),
    EVENT_NOT_OPEN(BAD_REQUEST, "Event_400_5", "아직 오픈되지 않은 전시에는 접근할 수 없습니다."),
    EVENT_TICKETING_TIME_IS_PASSED(BAD_REQUEST, "Event_400_6", "전시 기간이 지나 티켓팅을 할 수 없습니다."),
    CANNOT_OPEN_EVENT(BAD_REQUEST, "Event_400_7", "전시 오픈 조건을 충족하지 않았습니다."),
    ALREADY_OPEN_STATUS(BAD_REQUEST, "Event_400_8", "이미 오픈 중인 전시입니다."),
//    TODO 정산은 삭제할까 고민중
//    ALREADY_CALCULATING_STATUS(BAD_REQUEST, "Event_400_9", "이미 정산중인 이벤트입니다."),
    ALREADY_CLOSE_STATUS(BAD_REQUEST, "Event_400_10", "이미 종료된 전시입니다."),
    ALREADY_PREPARING_STATUS(BAD_REQUEST, "Event_400_11", "이미 준비중인 전시입니다."),
    ALREADY_DELETED_STATUS(BAD_REQUEST, "Event_400_12", "이미 삭제된 전시입니다."),
    CANNOT_DELETE_BY_ISSUED_TICKET(BAD_REQUEST, "Event_400_13", "발급 티켓이 있는 전시는 삭제할 수 없습니다."),
    CANNOT_DELETE_BY_OPEN_EVENT(BAD_REQUEST, "Event_400_14", "오픈 상태인 전시는 삭제할 수 없습니다."),
    OPEN_TIME_EXPIRED(BAD_REQUEST, "Event_400_15", "오픈 예정 시간이 현재 시간보다 빠릅니다."),

    USE_OTHER_API(BAD_REQUEST, "Event_400_8", "잘못된 접근입니다.");

    private Integer status;
    private String code;
    private String reason;

    @Override
    public ErrorReason getErrorReason() {
        return ErrorReason.builder().reason(reason).code(code).status(status).build();
    }

    @Override
    public String getExplainError() throws NoSuchFieldException {
        Field field = this.getClass().getField(this.name());
        ExplainError annotation = field.getAnnotation(ExplainError.class);
        return Objects.nonNull(annotation) ? annotation.value() : this.getReason();
    }
}
