package com.github.backend1st.web.controller;

import com.github.backend1st.repository.member.CustomUserDetails;
import com.github.backend1st.service.CommentsService;
import com.github.backend1st.web.dto.CommentsRequestDto;
import com.github.backend1st.web.dto.CommentsResponseDto;
import com.github.backend1st.web.dto.ResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Slf4j
@RestController
@RequestMapping("/api/comments")
@RequiredArgsConstructor
public class CommentsController {

    private final CommentsService commentsService;

    // 댓글등록
    @PostMapping
    public ResponseEntity<ResponseDto> insertComment(@RequestBody CommentsRequestDto commentsRequestDto, @AuthenticationPrincipal CustomUserDetails userDetails){
        commentsService.insertComment(commentsRequestDto, userDetails.getMemberId());
        ResponseDto responseDto = ResponseDto.builder().status(HttpStatus.OK.toString()).message("댓글이 성공적으로 작성되었습니다.").build();
        return ResponseEntity.ok(responseDto);

    }
    // 댓글조회
    @GetMapping("/{postId}")
    public ResponseEntity<List<CommentsResponseDto>> getCommentList(@PathVariable Long postId){
        List<CommentsResponseDto> commentsList = commentsService.findByCommentList(postId);
        return ResponseEntity.ok(commentsList);
    }

    // 댓글 전체조회
    @GetMapping
    public ResponseEntity<List<CommentsResponseDto>> getCommentList(){
        List<CommentsResponseDto> commentsList = commentsService.findByCommentList();
        return ResponseEntity.ok(commentsList);
    }


    // 댓글 수정
    @PutMapping("/{commentId}")
    public ResponseEntity<ResponseDto> updateComment(@RequestBody CommentsRequestDto commentsRequestDto, @PathVariable Long commentId){
        commentsService.updateComment(commentId, commentsRequestDto);
        ResponseDto responseDto = ResponseDto.builder().status(HttpStatus.OK.toString()).message("댓글이 성공적으로 수정되었습니다.").build();
        return ResponseEntity.ok(responseDto);
    }

    // 댓글 삭제
    @DeleteMapping("/{commentId}")
    public ResponseEntity<ResponseDto> deleteComment(@PathVariable Long commentId){
        commentsService.deleteComment(commentId);
        ResponseDto responseDto = ResponseDto.builder().status(HttpStatus.OK.toString()).message("댓글이 성공적으로 삭제되었습니다.").build();
        return ResponseEntity.ok(responseDto);
    }


}
