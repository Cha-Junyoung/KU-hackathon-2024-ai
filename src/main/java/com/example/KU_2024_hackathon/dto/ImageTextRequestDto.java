package com.example.KU_2024_hackathon.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ImageTextRequestDto {
    private String question1;
    private String answer1;
    private String question2;
    private String answer2;
    private String question3;
    private String answer3;


}
