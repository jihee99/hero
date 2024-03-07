package com.ex.hero.member.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.ex.hero.member.exception.UserNotFoundException;
import com.ex.hero.member.model.AccountState;
import com.ex.hero.member.model.Member;
import com.ex.hero.member.repository.MemberRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommonMemberService {

	private final MemberRepository memberRepository;

	/*  */

	public Member queryMember(Long userId) {
		return memberRepository.findById(userId).orElseThrow(() -> UserNotFoundException.EXCEPTION);
	}

	/** Member id 리스트에 포함되어 있는 유저를 모두 가져오는 쿼리 */
	public List<Member> queryMemberListByUserIdIn(List<Long> userIdList) {
		return memberRepository.findAllByUserIdIn(userIdList);
	}

	/** 이메일로 유저를 가져오는 쿼리 */
	public Member queryUserByEmail(String email) {
		return memberRepository
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
