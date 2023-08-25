package com.github.backend1st.service;


import com.github.backend1st.repository.member.CustomUserDetails;
import com.github.backend1st.repository.member.Member;
import com.github.backend1st.repository.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Primary
@RequiredArgsConstructor
@Service
public class CustomUserDetailsService implements UserDetailsService { // 스프링 시큐리티에서 유저를 찾는 메소드를 제공하는 서비스
    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        //  1. 이메일로 db에서 회원 가져오기, 스프링 시큐리티의 유저로 리턴.
        Member member = memberRepository.findAllByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("회원을 찾을 수 없습니다."));

        return CustomUserDetails.builder()
                .memberId(member.getMemberId())
                .email(member.getEmail())
                .password(member.getPassword())
                .authorities(Collections.singletonList(new SimpleGrantedAuthority(member.getRole().toString())))
                .build();
    }
}
