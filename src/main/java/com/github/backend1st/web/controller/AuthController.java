package com.github.backend1st.web.controller;

import com.github.backend1st.service.AuthService;
import com.github.backend1st.service.exceptions.InvalidValueException;
import com.github.backend1st.web.dto.LoginRequest;
import com.github.backend1st.web.dto.RegisterRequest;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/v1/api/sign")
@RequiredArgsConstructor
@Slf4j
public class AuthController {
    private final AuthService authService;

    // 23.08.15 hyuna 토큰을 쿠키에 저장하고 삭제할때 토큰을 없애는 방식도 있음..

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

    @ApiOperation("로그아웃 API")
    @PostMapping(value = "/logout")
    public String logout(HttpServletRequest httpServletRequest){
        String token = httpServletRequest.getHeader("X-AUTH-TOKEN");

        if (token.isEmpty()) return "헤더에 토큰이 없습니다.";

        if (authService.logout(token) ) {
            return "로그아웃이 성공하였습니다.";
        }
        else {
            return "이미 로그아웃 되었습니다.";
        }
    }
} // end class
