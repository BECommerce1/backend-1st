package com.github.backend1st.web.dto;

import com.github.backend1st.repository.member.Member;
import com.github.backend1st.repository.posts.Posts;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class PostsRequestDto {

    private String title;
    private String content;

    // dto > entity
    public Posts toEntity(Member member){
        return Posts.builder()
                .title(title)
                .content(content)
                .member(member)
                .build();
    }
}
