package com.ex.hero.member.vo;

import java.time.LocalDateTime;
import java.util.UUID;

import com.ex.hero.member.model.Member;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MemberInfoVo {
	private final UUID memberId;
	private final String email;
	private final String password;
	private final String name;
	private final String phone;
	private final LocalDateTime createdAt;
	private final Boolean status;

	public static MemberInfoVo from(Member member){
		return MemberInfoVo.builder()
			.memberId(member.getId())
			.email(member.getEmail())
			.password(member.getPassword())
			.name(member.getName())
			.phone(member.getPhone())
			.createdAt(member.getCreatedAt())
			.status(member.getStatus())
			.build();
	}
}
