package com.github.backend1st.web.controller;

import com.github.backend1st.repository.likes.LikesEntity;
import com.github.backend1st.repository.likes.LikesJpaRepository;
import com.github.backend1st.repository.member.CustomUserDetails;
import com.github.backend1st.service.LikesService;
import com.github.backend1st.web.dto.Likes;
import com.github.backend1st.web.dto.ResponseDto;
import io.swagger.annotations.ApiOperation;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/api")
@RequiredArgsConstructor
@Slf4j
public class LikesController {
    private final LikesService likesService;

    @ApiOperation("좋아요 클릭시 값 저장")
    @PostMapping("/insert_like/{commentId}")
    public ResponseEntity<ResponseDto> registerLikes(@AuthenticationPrincipal CustomUserDetails customUserDetails, @PathVariable Long commentId){
        likesService.savaItem(customUserDetails.getMemberId(),commentId);
        ResponseDto responseDto = ResponseDto.builder().status(HttpStatus.OK.toString()).message("즇아요 클릭 저장되었습니다.").build();
        return ResponseEntity.ok(responseDto);
    }

    @ApiOperation("좋아요 재클릭시 값 삭제")
    @DeleteMapping("/delete_like/{commentId}")
    public ResponseEntity<ResponseDto> deleteLike(@AuthenticationPrincipal CustomUserDetails customUserDetails, @PathVariable Long commentId) {
        likesService.deleteLike(customUserDetails.getMemberId(), commentId);
        ResponseDto responseDto = ResponseDto.builder().status(HttpStatus.OK.toString()).message("즇아요 클릭 삭제되었습니다.").build();
        return ResponseEntity.ok(responseDto);
    }

    @ApiOperation("해당 댓글의 좋아요 전체 카운트")
    @GetMapping("/count_like/{commentId}")
    public Long countLike(@PathVariable("commentId") Long commentId){
        Long like_Count = likesService.countLike(commentId);
        return like_Count;
    }

    @ApiOperation("로그인 정보와 일치한 좋아요체크")
    @GetMapping("/check_like")
    public Boolean clickCheckLike(@AuthenticationPrincipal CustomUserDetails customUserDetails, @RequestParam("commentId") Long commentId){
        log.info("");
        Boolean check_State = likesService.existsByMemberIdAndReplyId(customUserDetails.getMemberId(),commentId);
        return check_State;
    }
}
