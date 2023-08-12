package com.github.backend1st.service;

import com.github.backend1st.repository.member.Member;
import com.github.backend1st.repository.member.MemberRepository;
import com.github.backend1st.repository.member.Role;
import com.github.backend1st.web.dto.LoginRequest;
import com.github.backend1st.web.dto.RegisterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(transactionManager = "tmJpa")
public class AuthService {
    private final MemberRepository memberRepository;

    public boolean signUp(RegisterRequest registerRequest) {
        // TODO : 추후 Jwt 리팩토링
        // TODO : null point exception...

        String email = registerRequest.getEmail();

        // 1. email 동일 체크하기
        if (memberRepository.existsByEmail(email)) {
            return false; // email 동일 존재
        }

        // 2. 회원 등록
        Member member = Member.builder()
                .email(email)
                .password(registerRequest.getPassword())
                // email이 admin@admin.com일 경우 ROLE_ADMIN으로 등록..
                .role((email.equals("admin@admin.com") ? Role.ROLE_ADMIN : Role.ROLE_USER))
                .build();

        memberRepository.save(member);

        return true;
    }

    public String login(LoginRequest loginRequest) {
        // TODO


        return "";

    }
}
