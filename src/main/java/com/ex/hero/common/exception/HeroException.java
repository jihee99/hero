package com.ex.hero.common.exception;

import com.ex.hero.common.dto.ErrorReason;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class HeroException extends RuntimeException{
	public BaseErrorCode errorCode;

	public ErrorReason getErrorReason() {return this.errorCode.getErrorReason();}
}
