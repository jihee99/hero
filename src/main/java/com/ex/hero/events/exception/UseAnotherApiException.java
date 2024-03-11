package com.ex.hero.events.exception;

import com.ex.hero.common.exception.HeroException;

public class UseAnotherApiException extends HeroException {
	public static final HeroException EXCEPTION = new UseAnotherApiException();
	private UseAnotherApiException() {super(EventErrorCode.USE_ANOTHER_API);}
}
