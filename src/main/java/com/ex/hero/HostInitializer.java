 package com.ex.hero;

 import java.time.LocalDateTime;

 import com.ex.hero.group.model.Group;
 import com.ex.hero.group.repository.GroupRepository;
 import org.springframework.boot.ApplicationArguments;
 import org.springframework.boot.ApplicationRunner;
 import org.springframework.security.crypto.password.PasswordEncoder;
 import org.springframework.stereotype.Component;

 import com.ex.hero.member.model.Member;
 import com.ex.hero.member.model.MemberType;
 import com.ex.hero.member.repository.MemberRepository;
 import com.ex.hero.member.repository.SellerRepository;

 import lombok.RequiredArgsConstructor;

 @RequiredArgsConstructor
 @Component
 public class HostInitializer implements ApplicationRunner {
 	private final MemberRepository memberRepository;
 	private final GroupRepository groupRepository;
 	private final PasswordEncoder passwordEncoder;

 	@Override
 	public void run(ApplicationArguments args) {
 		Member member = memberRepository.save(Member.builder()
 			.email("masterhost@master.com")
 			.password(passwordEncoder.encode("1234"))
 			.name("마스터호스트")
 			.role(MemberType.HOST)
            .status(Boolean.TRUE)
 			.createdAt(LocalDateTime.now())
 			.build());

 		groupRepository.save(Group.builder()
 			.contactEmail(member.getEmail())
 			.name("테스트용 호스트")
 			.masterUserId(member.getUserId())
 			.build()
 		);

 	}

 }
