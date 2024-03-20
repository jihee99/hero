package com.ex.hero.security.principal;

import com.ex.hero.member.exception.UserNotFoundException;
import com.ex.hero.member.model.Member;
import com.ex.hero.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = memberRepository.findByEmail(username).orElseThrow(() -> UserNotFoundException.EXCEPTION);
//        return new AuthDetails(member.getEmail(), member.getAccountRole().toString());
        return new AuthDetails(member, member.getEmail());
    }
}
