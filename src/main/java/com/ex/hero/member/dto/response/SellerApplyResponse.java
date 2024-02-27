package com.ex.hero.member.dto.response;

import com.ex.hero.member.model.Member;
import com.ex.hero.member.model.Seller;
import com.ex.hero.member.model.SellerApplyType;

import io.swagger.v3.oas.annotations.media.Schema;

public record SellerApplyResponse (
	@Schema(description = "판매자 등록 신청 성공 여부", example = "true")
	boolean result,
	@Schema(description = "회원 이메일", example = "gildong2")
	String account,
	@Schema(description = "신청 상태", example = "APPLY")
	SellerApplyType applyType
) {
	public static SellerApplyResponse of(boolean result, Seller seller) {
		return new SellerApplyResponse(result, seller.getMember().getAccount(), seller.getApplyType());
	}
}
