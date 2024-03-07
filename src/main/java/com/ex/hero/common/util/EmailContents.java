package com.ex.hero.common.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;

@PropertySource("classpath:auth-jwt.yml")
public class EmailContents {

	@Value("${sender-address}")
	private static String senderAddress;

	public static final String SENDER_ADDRESS = senderAddress;

	public static final String MESSAGE_SUBJECT = "HERO 호스트 초대메일입니다.";

	public static final String MESSAGE_PREFIX = ""
		+ "<div style='font-family: Arial, sans-serif; font-size: 13px; text-align: center; '>"
		+ "<div style='margin: 20px;'>"
		+ "<h3 style='margin: 50px 0px 50px 0px;'>호스트 매니저 초대 메일</h3>"
		+ "<p></p>"
		+ "</div>"
		+ "<div><p>";

	public static final String MESSAGE_SUFFIX = ""
		+ "</p></div>"
		+ "</div>";
}
