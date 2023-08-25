package com.github.backend1st.web.dto;

import com.github.backend1st.repository.comments.Comments;
import com.github.backend1st.repository.member.Member;
import com.github.backend1st.repository.posts.Posts;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CommentsResponseDto {

    private Long postId;    // 게시글아이디
    private Long commentId; // 댓글아이디
    private String content; // 댓글내용
    private String author;  // 작성자
    private LocalDateTime createAt; // 작성일자
    private LocalDateTime updateAt; // 수정일자

    @Builder
    public CommentsResponseDto(Comments comments){
        this.commentId = comments.getCommentId();
        this.content = comments.getContent();
        this.author = comments.getMember().getEmail();
        this.createAt = comments.getCreateAt();
        this.updateAt = comments.getUpdateAt();
        this.postId = comments.getPosts().getPostId();
    }

}
