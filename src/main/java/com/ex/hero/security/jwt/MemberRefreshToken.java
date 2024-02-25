package com.ex.hero.security.jwt;

import java.util.UUID;

import com.ex.hero.member.model.Member;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberRefreshToken {
	@Id
	private UUID memberId;
	@OneToOne(fetch = FetchType.LAZY)
	@MapsId
	@JoinColumn(name = "id")
	private Member member;

	private String refreshToken;
	private int reissueCount = 0;

	public MemberRefreshToken(Member member, String refreshToken){
		this.member = member;
		this.refreshToken = refreshToken;
	}

	public void updateRefreshToken(String refreshToken){
		this.refreshToken = refreshToken;
	}

	public boolean validateRefreshToken(String refreshToken) {
		return this.refreshToken.equals(refreshToken);
	}

	public void increaseReissueCount() {
		reissueCount++;
	}

}
