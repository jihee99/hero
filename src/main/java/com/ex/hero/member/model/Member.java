package com.ex.hero.member.model;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.data.annotation.CreatedDate;

import com.ex.hero.member.dto.signup.request.SignUpRequest;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.*;


@Entity
@Table(name = "members")
@Builder
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Member {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	// private Long id;
	private UUID id;

	@Column(nullable = false, unique = true)
	private String account;
	@Column(nullable = false)
	private String password;
	@Column(nullable = false)
	private String name;
	private String phone;
	private String email;
	@Column(name = "role")
	@Enumerated(EnumType.STRING)
	private MemberType role;
	@CreatedDate
	private LocalDateTime createdAt;
	private Boolean status;


	public static Member createMember(String account, String name, String password, String phone, MemberType role) {
		Member member = new Member();
		member.setAccount(account);
		member.setName(name);
		member.setPassword(password);
		member.setPhone(phone);
		member.setRole(role);
		member.setCreatedAt(LocalDateTime.now());
		member.setStatus(Boolean.TRUE);
		return member;
	}

	public static Member from(SignUpRequest request) {
		return Member.builder()
			.account(request.account())
			.password(request.password())
			.name(request.name())
			.role(MemberType.USER)
			.createdAt(LocalDateTime.now())
			.build();
	}

}
