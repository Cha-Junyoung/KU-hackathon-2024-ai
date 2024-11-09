package com.example.KU_2024_hackathon.exception;

import com.example.KU_2024_hackathon.dto.ErrorDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionManager
{
    // 커스텀 예외 처리
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorDto> customExceptionHandler(CustomException e)
    {
        return ResponseEntity.status(e.getError_code().getHttpStatus())
                .body(
                        ErrorDto.builder()
                                .code(e.getError_code().toString())
                                .message(e.getError_code().getMessage())
                                .data(e.getData())
                                .build()
                );
    }

    // @Valid 어노테이션으로 수행한 유효성 검사 예외 처리
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDto> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e)
    {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(
                        ErrorDto.builder()
                                .code("INVALID_DATA")
                                .message("잘못된 형식의 요청 데이터입니다.")
                                .data(null)
                                .build()
                );
    }

    // 원인 불명으로 발생한 예외 처리
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDto> unexpectedExceptionHandler(Exception e)
    {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(
                        ErrorDto.builder()
                                .code("UNEXPECTED_ERROR")
                                .message(e.getMessage())
                                .data(null)
                                .build()
                );
    }
}
