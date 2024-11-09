package com.example.KU_2024_hackathon.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum CustomErrorCode
{
    ALREADY_USED_ID(HttpStatus.BAD_REQUEST, "이미 사용 중인 아이디입니다.");

    private final HttpStatus httpStatus;
    private final String message;
}
