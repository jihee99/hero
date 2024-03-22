package com.ex.hero.events.exception;

import com.ex.hero.common.annotation.ExplainError;
import com.ex.hero.common.dto.ErrorReason;
import com.ex.hero.common.exception.BaseErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.lang.reflect.Field;
import java.util.Objects;

@Getter
@AllArgsConstructor
public enum EventErrorCode implements BaseErrorCode {
    EVENT_NOT_FOUND(HttpStatus.NOT_FOUND, 4000, "이벤트를 찾을 수 없습니다."),
    HOST_NOT_AUTH_EVENT(HttpStatus.BAD_REQUEST, 4001, "Group Not Auth Event."),
    EVENT_CANNOT_END_BEFORE_START(HttpStatus.BAD_REQUEST, 4002, "시작 시각은 종료 시각보다 빨라야 합니다."),
    EVENT_URL_NAME_ALREADY_EXIST(HttpStatus.BAD_REQUEST, 4003, "중복된 URL 표시 이름입니다."),
    CANNOT_MODIFY_OPEN_EVENT(HttpStatus.BAD_REQUEST, 4004, "오픈된 전시 정보는 수정할 수 없습니다."),
    EVENT_NOT_OPEN(HttpStatus.BAD_REQUEST, 4005, "아직 오픈되지 않은 전시에는 접근할 수 없습니다."),
    EVENT_TICKETING_TIME_IS_PASSED(HttpStatus.BAD_REQUEST, 4006, "전시 기간이 지나 티켓팅을 할 수 없습니다."),
    CANNOT_OPEN_EVENT(HttpStatus.BAD_REQUEST, 4007, "전시 오픈 조건을 충족하지 않았습니다."),
    ALREADY_OPEN_STATUS(HttpStatus.BAD_REQUEST, 4008, "이미 오픈 중인 전시입니다."),
    USE_ANOTHER_API(HttpStatus.BAD_REQUEST, 4009, "잘못된 접근입니다."),
    ALREADY_CLOSE_STATUS(HttpStatus.BAD_REQUEST, 4010, "이미 종료된 전시입니다."),
    ALREADY_PREPARING_STATUS(HttpStatus.BAD_REQUEST, 4011, "이미 준비중인 전시입니다."),
    ALREADY_DELETED_STATUS(HttpStatus.BAD_REQUEST, 4012, "이미 삭제된 전시입니다."),
    CANNOT_DELETE_BY_ISSUED_TICKET(HttpStatus.BAD_REQUEST, 4013, "발급 티켓이 있는 전시는 삭제할 수 없습니다."),
    CANNOT_DELETE_BY_OPEN_EVENT(HttpStatus.BAD_REQUEST, 4014, "오픈 상태인 전시는 삭제할 수 없습니다."),
    OPEN_TIME_EXPIRED(HttpStatus.BAD_REQUEST, 4015, "오픈 예정 시간이 현재 시간보다 빠릅니다.");

    private final HttpStatus httpStatus;
    private final int code;
    private final String errorMessage;

    @Override
    public ErrorReason getErrorReason() {
        return ErrorReason.builder().errorCode(code).errorMessage(errorMessage).build();
    }

    @Override
    public String getExplainError() throws NoSuchFieldException {
        Field field = this.getClass().getField(this.name());
        ExplainError annotation = field.getAnnotation(ExplainError.class);
        return Objects.nonNull(annotation) ? annotation.value() : this.getErrorMessage();
    }
}
