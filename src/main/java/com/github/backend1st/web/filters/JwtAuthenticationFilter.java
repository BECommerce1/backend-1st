package com.github.backend1st.web.filters;

import com.github.backend1st.config.CustomAccessDeniedHandler;
import com.github.backend1st.config.JwtTokenProvider;
import com.github.backend1st.repository.token.TokenRepository;
import com.github.backend1st.service.exceptions.CustomAuthenticationEntryPointException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.security.auth.login.CredentialExpiredException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtTokenProvider jwtTokenProvider;

    private final TokenRepository tokenRepository;

    // request에서 토큰을 추출한 후 해당 토큰이 통과하면 권한을 부여하고, 실패하면 권한을 부여하지 않고 다음 필터로 진행시킨다
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String jwtToken = jwtTokenProvider.resolveToken(request);

        // 유효한 토큰일 시 인증
        if (jwtToken != null && jwtTokenProvider.validateToken(jwtToken)) {
            // token 테이블에 토큰이 있으면 (true) 인증 진행, 없으면 무효화.
            if (tokenRepository.existsByToken(jwtToken) == Boolean.TRUE) {
                // request가 로그아웃이면 넘어가기?
                if (!request.getRequestURI().contains("logout")) {
                    Authentication auth = jwtTokenProvider.getAuthentication(jwtToken);

                    SecurityContextHolder.getContext().setAuthentication(auth);
                }
            }
            else {
                // 401 예외 던지기
                response.sendError(HttpStatus.UNAUTHORIZED.value());
            }
        }

        filterChain.doFilter(request, response);
    }
}
