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
                String.format("1번 질문은 %s 이고, 여기에 대한 응답은 %s 입니다. "
                        + "2번 질문은 %s 이고, 여기에 대한 응답은 %s 입니다. "
                        + "3번 질문은 %s 이고, 여기에 대한 응답은 %s 입니다. ",
                        q1, a1, q2, a2, q3, a3);

        log.info("Request Text: {}", requestText);

        // Step 1: OpenAI API로 일기 생성
        String generatedTextPrompt =
                requestText + "해당 내용을 정리하여 일기를 작성해주세요. "
                + "일기 내용은 400자 이내로 작성해주세요. "
                + "일기는 ~이다, ~했다, ~겠다 와 같은 말로 마무리하는 문장의 형식으로 작성해주세요. "
                + "주어진 질문과 응답에 기반하여 작성하고, 허구의 사실을 창작하여서는 절대 안됩니다. ";

        String textData = openAIService.generateText(generatedTextPrompt);
        textData = textData.replaceAll("\n", " ");

        log.info("Generated Text: {}", textData);

        // Step 2: 생성된 textData 로부터 감정 분석
        String analyzeEmotionPrompt =
                textData + "해당 내용의 감정을 분석해주세요. "
                        + "기쁨, 화남, 슬픔, 두려움, 감탄, 놀람, 호기심, 따분함 중 하나로 분류해주세요. "
                        + "감정은 해당 내용을 읽고 느낀 감정을 기반으로 분류해주세요. "
                        + "생성한 문장 첫 마디에 반드시 제시된 8개의 감정 중 하나를 출력하세요. ";

        String emotion = openAIService.analyzeEmotion(analyzeEmotionPrompt);
        log.info("Analyzed Emotion: {}", emotion);

        // Step 3: OpenAI API로 이미지 생성
        String generatedImagePrompt =
                emotion + " -> 해당 감정에 적절한 이미지를 생성해주세요 "
                + "이미지는 해당 내용에 나타나는 감정을 충분히 표현할 수 있는 이미지여야 합니다. "
                + "또한, 이미지는 예술작품처럼 아름답게 그려져야 합니다. "
                + "일러스트와 같은 단순한 이미지는 절대 생성해서는 안되며, "
                + "감정을 충분히 표현할 수 있는 풍부한 색채를 이용해주세요. ";

        byte[] imageData = openAIService.generateImage(generatedImagePrompt);

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
