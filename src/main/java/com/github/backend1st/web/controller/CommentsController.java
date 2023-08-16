package com.github.backend1st.web.controller;

import com.github.backend1st.service.CommentsService;
import com.github.backend1st.web.dto.CommentsRequestDto;
import com.github.backend1st.web.dto.CommentsResponseDto;
import com.github.backend1st.web.dto.ResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Slf4j
@RestController
@RequestMapping("/api/comments")
@RequiredArgsConstructor
public class CommentsController {

    private final CommentsService commentsService;

    // 댓글등록
    @PostMapping("")
    public ResponseEntity<ResponseDto> insertComment(@RequestBody CommentsRequestDto commentsRequestDto){
        Long memberId = 1l;
        commentsService.insertComment(commentsRequestDto, memberId);
        ResponseDto responseDto = ResponseDto.builder().status(HttpStatus.OK.toString()).message("댓글이 성공적으로 작성되었습니다.").build();
        return ResponseEntity.ok(responseDto);

    }

    /* REST API URI 다시설계, path말고 json으로 변경 */
    // 댓글 조회
    @GetMapping("/{postId}")
    public ResponseEntity<List<CommentsResponseDto>> getPost(@PathVariable Long postId){
        Long memberId = 1l;
        List<CommentsResponseDto> commentsList = commentsService.findByCommentList(postId, memberId);
        return ResponseEntity.ok(commentsList);
    }


}
