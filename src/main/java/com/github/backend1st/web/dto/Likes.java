package com.github.backend1st.web.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Likes {
    private Long memberId;
    private Long replyId;

}
