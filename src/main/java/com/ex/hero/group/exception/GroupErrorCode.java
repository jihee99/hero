package com.ex.hero.group.exception;

import static com.ex.hero.common.exception.HeroStatic.BAD_REQUEST;
import static com.ex.hero.common.exception.HeroStatic.NOT_FOUND;

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
public enum GroupErrorCode implements BaseErrorCode {

    NOT_MANAGER_GROUP(HttpStatus.BAD_REQUEST, 2000, "매니저 권한이 없는 유저입니다."),
    FORBIDDEN_GROUP(HttpStatus.BAD_REQUEST, 2001, "해당 그룹에 대한 접근 권한이 없습니다."),
    ALREADY_JOINED_GROUP(HttpStatus.BAD_REQUEST, 2002, "이미 가입되어 있는 유저입니다."),
    NOT_MASTER_GROUP(HttpStatus.BAD_REQUEST, 2003, "그룹 마스터 권한이 없는 유저입니다."),
    CANNOT_MODIFY_MASTER_GROUP_ROLE(HttpStatus.BAD_REQUEST, 2004, "그룹 마스터의 권한은 변경할 수 없습니다."),
    NOT_ACCEPTED_GROUP(HttpStatus.BAD_REQUEST, 2005, "아직 초대를 수락하지 않은 유저입니다."),
    GROUP_NOT_FOUND(HttpStatus.NOT_FOUND, 2006, "해당 그룹을 찾을 수 없습니다."),
    GROUP_USER_NOT_FOUND(HttpStatus.NOT_FOUND, 2007, "그룹에 가입된 유저가 아닙니다."),
    MESSAGING_SERVER_EXCEPTION(HttpStatus.NOT_FOUND, 2008, "Messaging Server 에 연결할 수 없습니다.");

    private HttpStatus httpStatus;
    private int errorCode;
    private String errorMessage;

    @Override
    public ErrorReason getErrorReason() {
        return ErrorReason.builder().errorCode(errorCode).errorMessage(errorMessage).build();
    }

    @Override
    public String getExplainError() throws NoSuchFieldException {
        Field field = this.getClass().getField(this.name());
        ExplainError annotation = field.getAnnotation(ExplainError.class);
        return Objects.nonNull(annotation) ? annotation.value() : this.getErrorMessage();
    }
}