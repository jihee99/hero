package com.ex.hero.member.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ex.hero.member.model.AccountState;
import com.ex.hero.member.model.Member;
import com.ex.hero.member.model.MemberType;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByEmail(String email);

    List<Member> findAllByAccountRole(MemberType role);

    Optional<Member> findByEmailAndAccountState(String email, AccountState accountState);

    List<Member> findAllByUserIdIn(List<Long> userId);

    List<Member> findByUserIdIn(List<Long> userIds);
}
