package com.github.backend1st.service.exceptions;

public class CustomAuthenticationEntryPointException extends RuntimeException{
    public CustomAuthenticationEntryPointException(String msg) {
        super(msg);
    }
}
