package com.github.backend1st.web.controller;

import com.github.backend1st.service.AuthService;
import com.github.backend1st.web.dto.LoginRequest;
import com.github.backend1st.web.dto.RegisterRequest;
import com.github.backend1st.web.dto.ResponseDto;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
public class AuthController {
    private final AuthService authService;

    @ApiOperation("이메일과 패스워드로 회원가입 API")
    @PostMapping(value = "/signup")
    public ResponseEntity<ResponseDto> register(@RequestBody RegisterRequest registerRequest){
        boolean isSuccess = authService.signUp(registerRequest);

        ResponseDto responseDto;

        if (isSuccess) {
            responseDto = ResponseDto.builder().status(HttpStatus.OK.toString()).message("회원가입이 완료되었습니다.").build();
            return ResponseEntity.ok(responseDto);
        }
        else {
            responseDto = ResponseDto.builder().status(HttpStatus.NOT_FOUND.toString()).message("회원가입에 실패하였습니다.").build();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseDto);
        }
    }

    @ApiOperation("이메일과 패스워드로 로그인 API")
    @PostMapping(value = "/login")
    public ResponseEntity<ResponseDto> login(@RequestBody LoginRequest loginRequest, HttpServletResponse httpServletResponse){
        String token = authService.login(loginRequest);
        httpServletResponse.setHeader("X-AUTH-TOKEN", token);
        ResponseDto responseDto = ResponseDto.builder().status(HttpStatus.OK.toString()).message("로그인이 성공적으로 완료되었습니다.").build();
        return ResponseEntity.ok(responseDto);
    }

    @ApiOperation("로그아웃 API")
    @PostMapping(value = "/logout")
    public ResponseEntity<ResponseDto> logout(HttpServletRequest httpServletRequest){
        String token = httpServletRequest.getHeader("X-AUTH-TOKEN");

        if (token == null) {
            return ResponseEntity
                    .ok(ResponseDto
                            .builder()
                            .status(HttpStatus.OK.toString())
                            .message("헤더에 토큰이 없습니다.")
                            .build());
        }

        if (authService.logout(token) ) {
            return ResponseEntity
                    .ok(ResponseDto
                            .builder()
                            .status(HttpStatus.OK.toString())
                            .message("로그아웃되었습니다.").build());
        }
        else {
            return ResponseEntity
                    .ok(ResponseDto
                            .builder()
                            .status(HttpStatus.OK.toString())
                            .message("이미 로그아웃 되었습니다.").build());
        }
    }
} // end class
