package com.ex.hero.common.util;

import java.util.List;
import java.util.UUID;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ex.hero.member.exception.InvalidMemberException;
import com.ex.hero.member.exception.SecurityContextNotFoundException;
import com.ex.hero.member.exception.UserNotFoundException;
import com.ex.hero.member.model.Member;
import com.ex.hero.member.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MemberUtils {

	private static SimpleGrantedAuthority anonymous = new SimpleGrantedAuthority("ROLE_ANONYMOUS");
	private static SimpleGrantedAuthority swagger = new SimpleGrantedAuthority("ROLE_SWAGGER");
	private static List<SimpleGrantedAuthority> notUserAuthority = List.of(anonymous, swagger);
	private final MemberRepository memberRepository;

	public static Long getCurrentMemberId(){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		if (authentication == null) {
			throw SecurityContextNotFoundException.EXCEPTION;
		}

		if (authentication.isAuthenticated()
				&& !CollectionUtils.containsAny(
				authentication.getAuthorities(), notUserAuthority)) {
			return Long.valueOf(authentication.getName());
		}
		// 스웨거 유저일시 익명 유저 취급
		// 익명유저시 userId 0 반환
		return 0L;
	}

	public Member getCurrentMember(){
		return memberRepository.findById(getCurrentMemberId()).orElseThrow(() -> UserNotFoundException.EXCEPTION);
	}
}
