package com.github.backend1st.web.controller;

import com.github.backend1st.service.AuthService;
import com.github.backend1st.service.PostsService;
import com.github.backend1st.web.dto.PostsRequestDto;
import com.github.backend1st.web.dto.PostsResponseDto;
import com.github.backend1st.web.dto.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostsController {

    private final PostsService postsService;
    private final AuthService authService;

    /* 회원 객체 추가 */
    // 게시글 등록
    @PostMapping("")
    public ResponseEntity<ResponseDto> insertPost(@RequestBody PostsRequestDto postsRequestDto){
        Long memberId = 1l;
        postsService.insertPost(postsRequestDto);
        ResponseDto responseDto = ResponseDto.builder().status(HttpStatus.OK.toString()).message("게시물이 성공적으로 작성되었습니다.").build();
        return ResponseEntity.ok(responseDto);
    }

    // 게시글 조회
    @GetMapping("/{postId}")
    public ResponseEntity<PostsResponseDto> getPost(@PathVariable Long postId){
        PostsResponseDto postsResponseDto = postsService.findById(postId);
        return ResponseEntity.ok(postsResponseDto);
    }




}
