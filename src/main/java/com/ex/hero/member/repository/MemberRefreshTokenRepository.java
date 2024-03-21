package com.ex.hero.member.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ex.hero.security.jwt.MemberRefreshToken;

public interface MemberRefreshTokenRepository extends JpaRepository<MemberRefreshToken, Long> {

	Optional<MemberRefreshToken> findByMemberIdAndReissueCountLessThan(Long id, long count);

}
