package com.github.backend1st.web.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PostListResponseDto {
    private List<PostsResponseDto> posts;
}
