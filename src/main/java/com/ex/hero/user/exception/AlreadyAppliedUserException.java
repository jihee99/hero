package com.ex.hero.user.exception;

import com.ex.hero.common.exception.HeroException;

public class AlreadyAppliedUserException extends HeroException {

	public static final HeroException EXCEPTION = new AlreadyAppliedUserException();

	private AlreadyAppliedUserException() {
		super(UserErrorCode.ALREADY_APPLIED_USER);
	}

}
