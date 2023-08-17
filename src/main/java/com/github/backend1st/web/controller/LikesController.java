package com.github.backend1st.web.controller;

import com.github.backend1st.repository.likes.LikesEntity;
import com.github.backend1st.repository.likes.LikesJpaRepository;
import com.github.backend1st.repository.member.CustomUserDetails;
import com.github.backend1st.service.LikesService;
import com.github.backend1st.web.dto.Likes;
import io.swagger.annotations.ApiOperation;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/api")
@RequiredArgsConstructor
@Slf4j
public class LikesController {
    private final LikesService likesService;

    @ApiOperation("좋아요 클릭시 값 저장")
    @PostMapping("/insert_like/{replyId}")
    public String registerLikes(@AuthenticationPrincipal CustomUserDetails customUserDetails, @PathVariable Long replyId){

        long memberId = customUserDetails.getMemberId();
        String message= likesService.savaItem(memberId,replyId);
        return message;
    }

    @ApiOperation("좋아요 재클릭시 값 삭제")
    @DeleteMapping("/delete_like/{replyId}")
    public String deleteLike(@AuthenticationPrincipal CustomUserDetails customUserDetails, @PathVariable Long replyId) {
        long memberId = customUserDetails.getMemberId();
        Long deleteNo = likesService.deleteLike(memberId, replyId);
        return deleteNo+"번 삭제 완료";
    }
    //해당 댓글의 좋아요 전체 카운트
    @ApiOperation("해당 댓글의 좋아요 전체 카운트")
    @GetMapping("/count_like/{replyId}")
    public Long countLike(@PathVariable("replyId") Long replyId){
        Long like_Count = likesService.countLike(replyId);
        return like_Count;
    }
    //현재 좋아요 클릭 했는지 클릭 여부
    @ApiOperation("로그인 정보와 일치한 좋아요체크")
    @GetMapping("/check_like")
    public Boolean clickCheckLike(@AuthenticationPrincipal CustomUserDetails customUserDetails, @RequestParam("replyId") Long replyId){
        long memberId = customUserDetails.getMemberId();
        Boolean check_State = likesService.existsByMemberIdAndReplyId(memberId,replyId);
        return check_State;
    }
}
