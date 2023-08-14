package com.github.backend1st.web.controller;

import com.github.backend1st.service.exceptions.CustomAuthenticationEntryPointException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/exceptions")
public class ExceptionController {

    @GetMapping("/entrypoint")
    public void entryPointException() {
        throw new CustomAuthenticationEntryPointException("사용자 인증 과정에서 에러가 발생했습니다.");
    }

    @GetMapping("/access-denied")
    public void accessDeniedException() {
        throw new AccessDeniedException("사용자 인증이 거부되었습니다.");
    }
}
