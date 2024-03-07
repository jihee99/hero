package com.ex.hero.member.model;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.*;
import org.hibernate.annotations.Comment;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.ex.hero.member.dto.request.MemberUpdateRequest;
import com.ex.hero.member.dto.response.MemberInfoResponse;

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
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="seller_id")
	private Long id;

	@JoinColumn(name="member_id")
	private Long memberId;
	@Enumerated(EnumType.STRING)
	@Comment("승인 타입 (신청|거절|승인)")
	private SellerApplyType applyType;
	@Comment("등록일")
	private LocalDateTime applyAt;
	@Comment("승인일")
	private LocalDateTime approveAt;



//
//	public static Seller from(Member member){
//		return Seller.builder()
//			.member(member)
//			.applyType(SellerApplyType.APPLY)
//			.applyAt(LocalDateTime.now())
//			.build();
//	}
//
//	public Seller(MemberInfoResponse memberInfo){
//		Member member = new Member();
//		member.setId(memberInfo.id());
//		member.setAccount(memberInfo.account());
//		member.setName(memberInfo.name());
//		member.setEmail(memberInfo.email());
//		member.setRole(memberInfo.role());
//		member.setCreatedAt(memberInfo.createdAt());
//
//		this.member = member;
//		this.applyType = SellerApplyType.APPLY;
//		this.applyAt = LocalDateTime.now();
//	}

}
