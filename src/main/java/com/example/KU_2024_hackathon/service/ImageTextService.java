package com.example.KU_2024_hackathon.service;

import com.example.KU_2024_hackathon.dto.ImageTextResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class ImageTextService {
    private final OpenAIService openAIService;
    private final S3Service s3Service;

    public ImageTextResponseDto createTextWithImages(String q1, String a1, String q2, String a2, String q3, String a3) throws IOException {
        // Step 0: 텍스트 생성
        String requestText =
                q1 + " 에 대한 응답은 " + a1 + " 이고, "
                + q2 + " 에 대한 응답은 " + a2 + " 이고, "
                + q3 + " 에 대한 응답은 " + a3 + " 입니다. ";

        log.info("requestText: {}", requestText);

        // Step 1: OpenAI API로 일기 생성
        String generatedTextPrompt =
                requestText + "해당 내용을 정리하여 일기를 작성해주세요.";


        String textData = openAIService.generateText(generatedTextPrompt);

        // 생성된 문자열 내부의 모든 줄 바꿈 문자 제거
        textData = textData.replaceAll("\n", " ");

        // Step 2: OpenAI API로 이미지 생성
        String generatedImagePrompt =
                requestText + "해당 내용에 적절한 이미지를 생성해주세요";

        byte[] imageData = openAIService.generateImage(generatedImagePrompt);

        // Step 3: 생성된 textData 로부터 감정 분석
        String analyzeEmotionPrompt =
                textData + "해당 내용의 감정을 분석해주세요."
                + "기쁨, 화남, 슬픔, 두려움, 감탄, 놀람, 호기심, 따분함 중 하나로 분류해주세요.";

        String emotion = openAIService.analyzeEmotion(analyzeEmotionPrompt);

        // Step 4: S3에 이미지 업로드
        String imageUrl = s3Service.uploadImage(imageData, "diary_image_" + UUID.randomUUID() + ".png");

        // Step 5: JSON 형태로 반환
        return ImageTextResponseDto.builder()
                .emotion(emotion)
                .image(imageUrl)
                .text(textData)
                .build();
    }

}
