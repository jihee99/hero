package com.ex.hero.member.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.ex.hero.config.redis.CacheNames;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.ex.hero.member.model.AccountState;
import com.ex.hero.member.model.Member;
import com.ex.hero.member.model.MemberType;

public interface MemberRepository extends JpaRepository<Member, Long> {
//    @Cacheable(cacheNames = CacheNames.USERBYEMAIL, key = "'login'+#p0", unless = "#result==null")
    Optional<Member> findByEmail(String email);

    List<Member> findAllByAccountRole(MemberType role);

    Optional<Member> findByEmailAndAccountState(String email, AccountState accountState);

    List<Member> findAllByUserIdIn(List<Long> userId);

    List<Member> findByUserIdIn(List<Long> userIds);
}
