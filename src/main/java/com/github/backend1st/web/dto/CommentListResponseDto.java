package com.github.backend1st.web.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CommentListResponseDto {
    private List<CommentsResponseDto> comments;
}
