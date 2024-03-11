package com.ex.hero.member.model;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Profile {
	private String name;
	private String email;
	private String phoneNumber;

	public void withdraw() {
		this.name = "탈퇴한 유저";
		this.email = null;
		this.phoneNumber = null;
	}

	@Builder
	public Profile(String name, String email, String phoneNumber) {
		this.name = name;
		this.email = email;
		this.phoneNumber = phoneNumber;
	}
}
