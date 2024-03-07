package com.ex.hero.member.model;

import java.time.LocalDateTime;
import java.util.UUID;

import com.ex.hero.mail.dto.EmailUserInfo;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.ex.hero.member.dto.request.MemberUpdateRequest;
import com.ex.hero.member.dto.request.SignUpRequest;
import com.ex.hero.member.exception.ForbiddenUserException;
import com.ex.hero.member.vo.MemberInfoVo;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PostPersist;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.*;


@Entity
@Table(name = "tbl_member")
@Builder
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Member {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="member_id")
	private Long userId;

	@Column(nullable = false, unique = true)
	private String email;
	@Column(nullable = false)
	private String password;

	private String name;

	private String phoneNumber;

	@Enumerated(EnumType.STRING)
	private MemberType accountRole = MemberType.USER;

	@Enumerated(EnumType.STRING)
	private AccountState accountState = AccountState.NORMAL;

	@CreatedDate
	private LocalDateTime createdAt;


	private LocalDateTime lastLoginAt = LocalDateTime.now();


	public static Member from(SignUpRequest request, PasswordEncoder passwordEncoder) {
		return Member.builder()
			.email(request.email())
			.password(passwordEncoder.encode(request.password()))
			.name(request.name())
			.accountRole(MemberType.USER)
			.createdAt(LocalDateTime.now())
			.accountState(AccountState.NORMAL)
			.build();
	}

	public void login() {
		if (!AccountState.NORMAL.equals(this.accountState)) {
			throw ForbiddenUserException.EXCEPTION;
		}
		lastLoginAt = LocalDateTime.now();
	}


	public void update(MemberUpdateRequest newMember, PasswordEncoder passwordEncoder) {
		this.password = newMember.newPassword() == null || newMember.newPassword().isBlank()
			? this.password : passwordEncoder.encode(newMember.password());
		this.name = newMember.name();
		this.email = newMember.email();
		this.phoneNumber = newMember.phoneNumber();
	}

	public MemberInfoVo toMemberInfoVo() {
		return MemberInfoVo.from(this);
	}

	public EmailUserInfo toEmailUserInfo() {
		return new EmailUserInfo(this.name, this.email);
	}

	public Boolean isDeletedUser() {
		return accountState == AccountState.DELETED;
	}

	// @PostPersist
	// public void registerEvent() {
	// 	UserRegisterEvent userRegisterEvent = UserRegisterEvent.builder().userId(id).build();
	// 	Events.raise(userRegisterEvent);
	// }

}
