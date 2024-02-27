package com.ex.hero.member.model;

import java.time.LocalDateTime;

import org.hibernate.annotations.Comment;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "sellers")
@Builder
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Seller {

	@Id
	@OneToOne
	@JoinColumn(name = "id")
	private Member member;
	@Enumerated(EnumType.STRING)
	@Comment("승인 타입 (신청|거절|승인)")
	private SellerApplyType applyType;
	@Comment("등록일")
	private LocalDateTime applyAt;
	@Comment("승인일")
	private LocalDateTime approveAt;

}
