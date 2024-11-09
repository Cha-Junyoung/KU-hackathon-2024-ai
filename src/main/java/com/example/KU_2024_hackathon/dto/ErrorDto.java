package com.example.KU_2024_hackathon.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ErrorDto
{
    @NotBlank(message = "[code] cannot be blank.")
    @Schema(description = "에러 코드")
    private String code;

    @NotBlank(message = "[message] cannot be blank.")
    @Schema(description = "에러 메시지")
    private String message;

    @Schema(description = "데이터")
    private Object data;
}
