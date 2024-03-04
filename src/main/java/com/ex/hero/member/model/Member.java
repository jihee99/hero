package com.ex.hero.member.model;

import java.time.LocalDateTime;
import java.util.UUID;

import com.ex.hero.mail.dto.EmailUserInfo;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.ex.hero.member.dto.request.MemberUpdateRequest;
import com.ex.hero.member.dto.request.SignUpRequest;
import com.ex.hero.member.vo.MemberInfoVo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
	@Column(name="member_id")
	private UUID id;
	@Column(nullable = false, unique = true)
	private String email;
	@Column(nullable = false)
	private String password;
	@Column(nullable = false)
	private String name;
	private String phone;

	@Column(name = "role")
	@Enumerated(EnumType.STRING)
	private MemberType role;

	@CreatedDate
	private LocalDateTime createdAt;

	@ColumnDefault("TRUE")
	private Boolean status = Boolean.TRUE;


	public static Member from(SignUpRequest request, PasswordEncoder passwordEncoder) {
		return Member.builder()
			.email(request.email())
			.password(passwordEncoder.encode(request.password()))
			.name(request.name())
			.role(MemberType.USER)
			.createdAt(LocalDateTime.now())
			.status(Boolean.TRUE)
			.build();
	}

	public void update(MemberUpdateRequest newMember, PasswordEncoder passwordEncoder) {
		this.password = newMember.newPassword() == null || newMember.newPassword().isBlank()
			? this.password : passwordEncoder.encode(newMember.password());
		this.name = newMember.name();
		this.email = newMember.email();
		this.phone = newMember.phone();
	}

	public MemberInfoVo toUserInfoVo() {
		return MemberInfoVo.from(this);
	}

	public EmailUserInfo toEmailUserInfo() {
		return new EmailUserInfo(this.name, this.email);
	}

}
