package com.ex.hero.member.exception;

import org.aspectj.apache.bcel.classfile.CodeException;

import com.ex.hero.common.exception.BaseErrorCode;
import com.ex.hero.common.exception.HeroException;

public class AlreadyAppliedUserException extends HeroException {

	public static final HeroException EXCEPTION = new AlreadyAppliedUserException();

	private AlreadyAppliedUserException() {
		super(MemberErrorCode.ALREADY_APPLIED_USER);
	}

}
