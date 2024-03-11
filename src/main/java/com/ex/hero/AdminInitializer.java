package com.ex.hero;

import java.time.LocalDateTime;

import com.ex.hero.member.model.AccountState;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.ex.hero.member.model.Member;
import com.ex.hero.member.model.MemberType;
import com.ex.hero.member.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class AdminInitializer implements ApplicationRunner {
    private final MemberRepository memberRepository;

	private final PasswordEncoder passwordEncoder;

    @Override
    public void run(ApplicationArguments args) {

        Member admin = Member.builder()
                .email("admin@test.com")
                .password(passwordEncoder.encode("1234"))
                .name("관리자")
                .accountState(AccountState.NORMAL)
                .build();

        Member master = Member.builder()
                .email("master@test.com")
                .password(passwordEncoder.encode("1234"))
                .name("그룹마스터")
                .accountRole(MemberType.MASTER)
                .accountState(AccountState.NORMAL)
                .build();

        Member manager = Member.builder()
                .email("manager@test.com")
                .password(passwordEncoder.encode("1234"))
                .name("매니저")
                .accountRole(MemberType.MANAGER)
                .accountState(AccountState.NORMAL)
                .build();

        Member user = Member.builder()
                .email("user@test.com")
                .password(passwordEncoder.encode("1234"))
                .name("테스트사용자")
                .accountRole(MemberType.USER)
                .accountState(AccountState.NORMAL)
                .build();


        memberRepository.save(admin);
        memberRepository.save(master);
        memberRepository.save(manager);
        memberRepository.save(user);


    }
}
