package com.ex.hero.member.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ex.hero.member.model.Member;
import com.ex.hero.member.model.MemberType;

public interface MemberRepository extends JpaRepository<Member, UUID> {
    Optional<Member> findByEmail(String email);

    List<Member> findAllByRole(MemberType role);

    Optional<Member> findByEmailAndStatus(String email, Boolean status);

    List<Member> findAllByIdIn(List<UUID> id);

    List<Member> findByIdIn(List<UUID> userIds);
}
