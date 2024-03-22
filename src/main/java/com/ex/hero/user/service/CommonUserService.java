package com.ex.hero.user.service;

import com.ex.hero.user.exception.UserNotFoundException;
import com.ex.hero.user.model.AccountState;
import com.ex.hero.user.model.User;
import com.ex.hero.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommonUserService {

	private final UserRepository userRepository;


	public User queryMember(Long userId) {
		return userRepository.findById(userId).orElseThrow(() -> UserNotFoundException.EXCEPTION);
	}

	/** Member id 리스트에 포함되어 있는 유저를 모두 가져오는 쿼리 */
	public List<User> queryMemberListByUserIdIn(List<Long> userIdList) {
		return userRepository.findAllByUserIdIn(userIdList);
	}

	/** 이메일로 유저를 가져오는 쿼리 */
	public User queryUserByEmail(String email) {
		return userRepository
				.findByEmailAndAccountState(email, AccountState.NORMAL)
				.orElseThrow(() -> UserNotFoundException.EXCEPTION);
	}


	// public Long countNormalMemberCreatedBefore(LocalDateTime before) {
	// 	return memberRepository.countByStatusAndCreatedAtBefore(Boolean.TRUE, before);
	// }

	// public List<Member> findMemberByUserIdIn(List<UUID> userIds) {
	// 	return memberRepository.findByUserIdIn(userIds);
	// }

}
