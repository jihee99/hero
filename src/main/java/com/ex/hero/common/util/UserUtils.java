package com.ex.hero.common.util;

import com.ex.hero.user.exception.SecurityContextNotFoundException;
import com.ex.hero.user.exception.UserNotFoundException;
import com.ex.hero.user.model.User;
import com.ex.hero.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Component
@RequiredArgsConstructor
public class UserUtils {

	private static SimpleGrantedAuthority anonymous = new SimpleGrantedAuthority("ROLE_ANONYMOUS");
	private static SimpleGrantedAuthority swagger = new SimpleGrantedAuthority("ROLE_SWAGGER");
	private static List<SimpleGrantedAuthority> notUserAuthority = List.of(anonymous, swagger);
	private final UserRepository userRepository;

	public Long getCurrentMemberId(){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		if (authentication == null) {
			throw SecurityContextNotFoundException.EXCEPTION;
		}

		if (authentication.isAuthenticated() && !CollectionUtils.containsAny(authentication.getAuthorities(), notUserAuthority)) {
			User member = userRepository.findByEmail(authentication.getName())
					.orElseThrow(() -> UserNotFoundException.EXCEPTION);
			return member.getUserId();
		}
		// 스웨거 유저일시 익명 유저 취급
		// 익명유저시 userId 0 반환
		return 0L;
	}

	public User getCurrentMember(){
		return userRepository.findById(getCurrentMemberId()).orElseThrow(() -> UserNotFoundException.EXCEPTION);
	}

}
