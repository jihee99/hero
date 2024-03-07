package com.ex.hero.group.exception;

import com.ex.hero.common.annotation.ExplainError;
import com.ex.hero.common.dto.ErrorReason;
import com.ex.hero.common.exception.BaseErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.lang.reflect.Field;
import java.util.Objects;

import static com.ex.hero.common.exception.HeroStatic.BAD_REQUEST;
import static com.ex.hero.common.exception.HeroStatic.NOT_FOUND;

@Getter
@AllArgsConstructor
public enum GroupErrorCode implements BaseErrorCode {

    NOT_MANAGER_GROUP(BAD_REQUEST, "GROUP_400_1", "매니저 권한이 없는 유저입니다."),
    FORBIDDEN_GROUP(BAD_REQUEST, "GROUP_400_2", "해당 그룹에 대한 접근 권한이 없습니다."),
    ALREADY_JOINED_GROUP(BAD_REQUEST, "GROUP_400_3", "이미 가입되어 있는 유저입니다."),
    NOT_MASTER_GROUP(BAD_REQUEST, "GROUP_400_4", "그룹 마스터 권한이 없는 유저입니다."),
    CANNOT_MODIFY_MASTER_GROUP_ROLE(BAD_REQUEST, "GROUP_400_5", "그룹 마스터의 권한은 변경할 수 없습니다."),
    NOT_ACCEPTED_GROUP(BAD_REQUEST, "GROUP_400_6", "아직 초대를 수락하지 않은 유저입니다."),
    GROUP_NOT_FOUND(NOT_FOUND, "GROUP_404_1", "해당 그룹을 찾을 수 없습니다."),
    GROUP_USER_NOT_FOUND(NOT_FOUND, "GROUP_404_2", "그룹에 가입된 유저가 아닙니다."),
    MESSAGING_SERVER_EXCEPTION(NOT_FOUND, "GROUP_404_3", "Messaging Server 에 연결할 수 없습니다.");

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