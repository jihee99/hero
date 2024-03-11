package com.ex.hero.common.vo;

import java.time.LocalDateTime;

import com.ex.hero.member.model.AccountState;
import com.ex.hero.member.model.Member;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MemberInfoVo {
	private final Long memberId;
	private final String email;
	private final String password;
	private final String name;
	private final String phone;
	private final LocalDateTime createdAt;
	private final AccountState accountState;

	public static MemberInfoVo from(Member member){
		return MemberInfoVo.builder()
			.memberId(member.getUserId())
			.email(member.getEmail())
			.password(member.getPassword())
			.name(member.getName())
			.phone(member.getPhoneNumber())
			.createdAt(member.getCreatedAt())
			.accountState(member.getAccountState())
			.build();
	}
}
