package com.ex.hero.member.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ex.hero.security.jwt.MemberRefreshToken;

public interface MemberRefreshTokenRepository extends JpaRepository<MemberRefreshToken, UUID> {

	Optional<MemberRefreshToken> findByMemberIdAndReissueCountLessThan(UUID id, long count);

}
