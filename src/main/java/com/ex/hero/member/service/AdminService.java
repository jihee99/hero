package com.ex.hero.member.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ex.hero.member.dto.response.MemberInfoResponse;
import com.ex.hero.member.model.MemberType;
import com.ex.hero.member.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class AdminService {
    private final MemberRepository memberRepository;

    @Transactional(readOnly = true)
    public List<MemberInfoResponse> getMembers() {
        return memberRepository.findAllByRole(MemberType.USER).stream()
                .map(MemberInfoResponse::from)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<MemberInfoResponse> getSellers() {
        return memberRepository.findAllByRole(MemberType.HOST).stream()
            .map(MemberInfoResponse::from)
            .toList();
    }

    @Transactional(readOnly = true)
    public List<MemberInfoResponse> getAdmins() {
        return memberRepository.findAllByRole(MemberType.ADMIN).stream()
                .map(MemberInfoResponse::from)
                .toList();
    }


}
