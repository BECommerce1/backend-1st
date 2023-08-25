package com.github.backend1st.web.dto;

import lombok.*;

@Builder
@Getter
@Setter
public class ResponseDto {
    private String status;
    private String message;
}
