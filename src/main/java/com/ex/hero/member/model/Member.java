package com.ex.hero.member.model;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.data.annotation.CreatedDate;

import com.ex.hero.member.dto.request.MemberUpdateRequest;
import com.ex.hero.member.dto.request.SignUpRequest;

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


	public static Member from(SignUpRequest request) {
		return Member.builder()
			.account(request.account())
			.password(request.password())
			.name(request.name())
			.role(MemberType.USER)
			.createdAt(LocalDateTime.now())
			.build();
	}

	public void update(MemberUpdateRequest newMember) {
		this.password = newMember.newPassword() == null || newMember.newPassword().isBlank() ? this.password : newMember.password();
		this.name = newMember.name();
		this.email = newMember.email();
		this.phone = newMember.phone();
	}

}
