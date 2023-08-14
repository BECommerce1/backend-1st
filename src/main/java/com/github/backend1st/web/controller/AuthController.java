package com.github.backend1st.web.controller;

import com.github.backend1st.service.AuthService;
import com.github.backend1st.web.dto.LoginRequest;
import com.github.backend1st.web.dto.RegisterRequest;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/v1/api/sign")
@RequiredArgsConstructor
@Slf4j
public class AuthController {
    private final AuthService authService;

    @ApiOperation("이메일과 패스워드로 회원가입 API")
    @PostMapping(value = "/register")
    public String register(@RequestBody RegisterRequest registerRequest) {
        boolean isSuccess = authService.signUp(registerRequest);

        // TODO : 회원 가입 시 아예 메인으로 리다이렉트해주기
        return isSuccess ? "회원가입 성공하였습니다." : "회원가입에 실패하였습니다";
    }

    @ApiOperation("이메일과 패스워드로 로그인 API")
    @PostMapping(value = "/login")
    public String login(@RequestBody LoginRequest loginRequest, HttpServletResponse httpServletResponse){
        String token = authService.login(loginRequest);
        httpServletResponse.setHeader("X-AUTH-TOKEN", token);

        // TODO : 로그인 성공 시 메인으로 리다이렉트 해주기
        return "로그인이 성공하였습니다.";
    }
}
