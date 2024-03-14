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

	@ExplainError("회원가입시에 이미 회원가입한 유저일시 발생하는 오류.")
	USER_ALREADY_SIGNUP(HttpStatus.BAD_REQUEST, 4000, "이미 회원가입한 유저입니다."),
	@ExplainError("정지 처리된 유저일 경우 밣생하는 오류")
	USER_FORBIDDEN(HttpStatus.FORBIDDEN, 4001, "접근이 제한된 유저입니다."),
	@ExplainError("탈퇴한 유저로 접근하려는 경우")
	USER_ALREADY_DELETED(HttpStatus.FORBIDDEN, 4002, "이미 지워진 유저입니다."),
	@ExplainError("유저 정보를 찾을 수 없는 경우")
	USER_NOT_FOUND(HttpStatus.NOT_FOUND, 4003, "유저 정보를 찾을 수 없습니다."),
	USER_PHONE_INVALID(HttpStatus.BAD_REQUEST, 4004, "유저의 휴대폰 전화번호가 올바르지않습니다. 관리자에게 문의주세요"),
	@ExplainError("이미 판매자 등록신청을 한 경우")
	ALREADY_APPLIED_USER(HttpStatus.NOT_FOUND, 4005, "이미 신청한 회원입니다."),
	SECURITY_CONTEXT_NOT_FOUND(HttpStatus.NOT_FOUND, 4006, "security context not found"),
	@ExplainError("헤더에 올바른 accessToken을 담지않았을 때 발생하는 오류(형식 불일치 등)")
	ACCESS_TOKEN_NOT_EXIST(HttpStatus.FORBIDDEN, 4031, "알맞은 accessToken을 넣어주세요.");

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
