package com.ex.hero.events.exception;

import com.ex.hero.common.exception.HeroException;

public class UseOtherApiException extends HeroException {
	public static final HeroException EXCEPTION = new UseOtherApiException();
	private UseOtherApiException() {super(EventErrorCode.USE_OTHER_API);}
}
