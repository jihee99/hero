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
		+ "<p>아래의 버튼을 클릭해 초대를 수락할 수 있습니다.</p>"
		+ "</div>"
		+ "<div><a href='";

	public static final String MESSAGE_SUFFIX = ""
		+ "' style='background: #9f9f9f; display: inline-block; width: 150px; padding: 0; margin: 30px 10px 10px 0; font-weight: 600; text-align: center; line-height: 40px; color: #FFF; border-radius: 5px; transition: all 0.2s;'>초대 수락하기</a></div>"
		+ "</div>";
}
