package com.github.backend1st.service;

import com.github.backend1st.config.JwtTokenProvider;
import com.github.backend1st.repository.member.Member;
import com.github.backend1st.repository.member.MemberRepository;
import com.github.backend1st.repository.member.Role;
import com.github.backend1st.service.exceptions.NotAcceptException;
import com.github.backend1st.service.exceptions.NotFoundException;
import com.github.backend1st.web.dto.LoginRequest;
import com.github.backend1st.web.dto.RegisterRequest;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(transactionManager = "tmJpa")
public class AuthService {
    private final MemberRepository memberRepository;

    private final JwtTokenProvider jwtTokenProvider;

    private final AuthenticationManager authenticationManager;

    private final PasswordEncoder passwordEncoder;

    public boolean signUp(RegisterRequest registerRequest) {
        String email = registerRequest.getEmail();

        // 1. email 동일 체크하기
        if (memberRepository.existsByEmail(email)) {
            return false; // email 동일 존재
        }

        // 2. 회원 등록
        Member member = Member.builder()
                .email(email)
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                // email이 admin@admin.com일 경우 ROLE_ADMIN으로 등록..
                .role((email.equals("admin@admin.com") ? Role.ROLE_ADMIN : Role.ROLE_USER))
                .build();

        memberRepository.save(member);

        return true;
    }

    public String login(LoginRequest loginRequest) {
        String email = loginRequest.getEmail();
        String password = loginRequest.getPassword();

        try {
            // 1. SpringSecurity Context에 인증 정보 등록하기
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(email, password)
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);

            // 2. 이메일로 해당 회원 찾아 가져오고, 권한을 뽑아 토큰을 발급하기.
            Member member = memberRepository.findAllByEmail(email)
                    .orElseThrow(() -> new NotFoundException("회원 정보를 찾을 수 없습니다."));

            Role role = member.getRole();

            return jwtTokenProvider.createToken(email, role);

        } catch (Exception e) {
            e.printStackTrace();
            throw new NotAcceptException("로그인 할 수 없습니다.");
        }
    }
} // End class
