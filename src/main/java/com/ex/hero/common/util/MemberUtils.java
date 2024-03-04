package com.ex.hero.common.util;

import java.util.UUID;

import org.springframework.security.core.Authentication;
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

	private final MemberRepository memberRepository;

	public UUID getCurrentMemberId(){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		if (authentication == null) {
			throw SecurityContextNotFoundException.EXCEPTION;
		}

		if (!authentication.isAuthenticated()) {
			throw new InvalidMemberException();
		}

		return UUID.fromString(authentication.getName());
	}

	public Member getCurrentMember(){
		return memberRepository.findById(getCurrentMemberId()).orElseThrow(() -> UserNotFoundException.EXCEPTION);
	}
}
