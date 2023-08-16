package com.github.backend1st.web.controller;

import com.github.backend1st.service.CommentsService;
import com.github.backend1st.web.dto.CommentsRequestDto;
import com.github.backend1st.web.dto.ResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


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

}
