package com.github.backend1st.web.dto;

import com.github.backend1st.repository.posts.Posts;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
public class PostsResponseDto {
    private Long postId;
    private String title;
    private String content;
    private String author;
    private LocalDateTime createAt;

    @Builder
    public PostsResponseDto(Posts posts){
        this.postId = posts.getPostId();
        this.title = posts.getTitle();
        this.content = posts.getContent();
        this.author = posts.getMember().getEmail();
        this.createAt = posts.getCreateAt();
    }
}
