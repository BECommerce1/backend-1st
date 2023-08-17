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

import java.util.List;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostsController {

    private final PostsService postsService;
    private final AuthService authService;

    /* tobe memberId -> 시큐리티 객체로 변경 */

    // 게시글 등록
    @PostMapping
    public ResponseEntity<ResponseDto> insertPost(@RequestBody PostsRequestDto postsRequestDto){
        Long memberId = 1l;
        postsService.insertPost(postsRequestDto, memberId);
        ResponseDto responseDto = ResponseDto.builder().status(HttpStatus.OK.toString()).message("게시물이 성공적으로 작성되었습니다.").build();
        return ResponseEntity.ok(responseDto);
    }

    // 게시글 상세조회
    @GetMapping("/{postId}")
    public ResponseEntity<PostsResponseDto> getPost(@PathVariable Long postId){
        PostsResponseDto postsResponseDto = postsService.findById(postId);
        return ResponseEntity.ok(postsResponseDto);
    }

    // 게시글 전체조회
    @GetMapping
    public ResponseEntity<List<PostsResponseDto>> getPostList() {
       return ResponseEntity.ok(postsService.findAllDesc());
    }


    // 게시글 수정
    @PutMapping("/{postId}")
    public ResponseEntity<ResponseDto> updatePost(@RequestBody PostsRequestDto postsRequestDto, @PathVariable Long postId){
        Long memberId = 1l;
        postsService.updatePost(postsRequestDto, memberId, postId);
        ResponseDto responseDto = ResponseDto.builder().status(HttpStatus.OK.toString()).message("게시물이 성공적으로 수정되었습니다.").build();
        return ResponseEntity.ok(responseDto);
    }

    // 게시글 삭제
    @DeleteMapping("/{postId}")
    public ResponseEntity<ResponseDto> deletePost(@PathVariable Long postId){
        postsService.deletePost(postId);
        ResponseDto responseDto = ResponseDto.builder().status(HttpStatus.OK.toString()).message("게시물이 성공적으로 삭제되었습니다.").build();
        return ResponseEntity.ok(responseDto);
    }










}
