package com.github.backend1st.service;

import com.github.backend1st.config.JwtTokenProvider;
import com.github.backend1st.repository.token.Token;
import com.github.backend1st.repository.token.TokenRepository;
import com.github.backend1st.repository.member.Member;
import com.github.backend1st.repository.member.MemberRepository;
import com.github.backend1st.repository.member.Role;
import com.github.backend1st.service.exceptions.NotAcceptException;
import com.github.backend1st.service.exceptions.NotFoundException;
import com.github.backend1st.web.dto.LoginRequest;
import com.github.backend1st.web.dto.RegisterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(transactionManager = "tmJpa")
@RequiredArgsConstructor
public class AuthService {
    private final MemberRepository memberRepository;

    private final JwtTokenProvider jwtTokenProvider;

    private final AuthenticationManager authenticationManager;

    private final PasswordEncoder passwordEncoder;

    private final TokenRepository tokenRepository;

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

            // 23.08.16 로그인시 토큰 테이블에 해당 토큰을 insert
            // valid시, token 테이블에 해당 토큰이 없으면 무효된 것으로 처리.
            String token = jwtTokenProvider.createToken(email, role);

            tokenRepository.save(
                    Token.builder()
                            .token(token)
                            .build()
            );

            return token;

        } catch (Exception e) {
            e.printStackTrace();
            throw new NotAcceptException("로그인 할 수 없습니다.");
        }
    }

    public boolean logout(String token) {
        // 로그아웃 로직
        // token 테이블에서 토큰이 존재하면 delete
        // blacklist table에 해당 토큰이 있는지 검사
        if (!tokenRepository.existsByToken(token)) return false;

        // 블랙리스트 db에서 Delete
        if (tokenRepository.deleteByToken(token) == 0) return false;

        return true;
    }
} // End class
