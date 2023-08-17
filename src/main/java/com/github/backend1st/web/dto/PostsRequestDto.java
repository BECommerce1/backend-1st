package com.github.backend1st.web.dto;

import com.github.backend1st.repository.posts.Posts;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/* 게시판 요청 dto */
@Builder
@Getter
@Setter
public class PostsRequestDto {

    private String title;
    private String content;
    private String author;


    // dto > entity
    public Posts toEntity(){
        return Posts.builder()
                .title(title)
                .content(content)
                .author(author)
                .build();
    }
}
