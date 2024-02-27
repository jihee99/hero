package com.ex.hero.member.exception;

import static com.ex.hero.common.exception.HeroStatic.*;

import java.lang.reflect.Field;
import java.util.Objects;

import org.springframework.http.HttpStatus;

import com.ex.hero.common.annotation.ExplainError;
import com.ex.hero.common.dto.ErrorReason;
import com.ex.hero.common.exception.BaseErrorCode;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MemberErrorCode implements BaseErrorCode {

	@ExplainError("회원가입시에 이미 회원가입한 유저일시 발생하는 오류. 회원가입전엔 항상 register valid check 를 해주세요")
	USER_ALREADY_SIGNUP(BAD_REQUEST, "USER_400_1", "이미 회원가입한 유저입니다."),
	@ExplainError("정지 처리된 유저일 경우 밣생하는 오류")
	USER_FORBIDDEN(FORBIDDEN, "USER_403_1", "접근이 제한된 유저입니다."),
	@ExplainError("탈퇴한 유저로 접근하려는 경우")
	USER_ALREADY_DELETED(FORBIDDEN, "USER_403_2", "이미 지워진 유저입니다."),
	@ExplainError("유저 정보를 찾을 수 없는 경우")
	USER_NOT_FOUND(NOT_FOUND, "USER_404_1", "유저 정보를 찾을 수 없습니다."),
	USER_PHONE_INVALID(BAD_REQUEST, "USER_404_2", "유저의 휴대폰 전화번호가 올바르지않습니다. 관리자에게 문의주세요"),
	@ExplainError("이미 판매자 등록신청을 한 경우")
	ALREADY_APPLIED_USER(NOT_FOUND, "USER_405_1", "이미 신청한 회원입니다.");


	private final Integer status;
	private final String code;
	private final String reason;

	@Override
	public ErrorReason getErrorReason() {
		return ErrorReason.builder()
			.reason(reason)
			.code(code)
			.status(status)
			.build();
	}

	@Override
	public String getExplainError() throws NoSuchFieldException {
		Field field = this.getClass().getField(this.name());
		ExplainError annotation = field.getAnnotation(ExplainError.class);
		return Objects.nonNull(annotation) ? annotation.value() : this.getReason();
	}
}
