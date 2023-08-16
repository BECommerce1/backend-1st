package com.github.backend1st.web.dto;

import com.github.backend1st.repository.comments.Comments;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class CommentsRequestDto {

    private String content; // 댓글내용
    private Long postId;

    public Comments toEntity(){
        return Comments.builder()
                .content(content)
                .build();
    }



}
