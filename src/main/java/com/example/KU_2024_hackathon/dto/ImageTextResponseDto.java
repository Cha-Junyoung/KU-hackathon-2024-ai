package com.example.KU_2024_hackathon.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ImageTextResponseDto {
    private String emotion;
    private String image;
    private String text;
}
