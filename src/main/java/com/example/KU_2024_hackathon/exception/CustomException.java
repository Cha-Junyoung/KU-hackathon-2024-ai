package com.example.KU_2024_hackathon.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CustomException extends RuntimeException
{
    private CustomErrorCode error_code;
    private Object data;
}
