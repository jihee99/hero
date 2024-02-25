// package com.ex.hero;
//
// import java.time.LocalDateTime;
//
// import org.springframework.boot.ApplicationArguments;
// import org.springframework.boot.ApplicationRunner;
// import org.springframework.stereotype.Component;
//
// import com.ex.hero.member.model.Member;
// import com.ex.hero.member.model.MemberType;
// import com.ex.hero.member.repository.MemberRepository;
//
// import lombok.RequiredArgsConstructor;
//
// @RequiredArgsConstructor
// @Component
// public class AdminInitializer implements ApplicationRunner {
//     private final MemberRepository memberRepository;
//
//     @Override
//     public void run(ApplicationArguments args) {
//         memberRepository.save(Member.builder()
//                 .account("admin")
//                 .password("admin")
//                 .name("관리자")
//                 .role(MemberType.ADMIN)
//                 .createdAt(LocalDateTime.now())
//                 .build());
//     }
// }
