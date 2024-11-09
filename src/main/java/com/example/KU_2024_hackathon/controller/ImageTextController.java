package com.example.KU_2024_hackathon.controller;

import com.example.KU_2024_hackathon.dto.ImageTextRequestDto;
import com.example.KU_2024_hackathon.dto.ImageTextResponseDto;
import com.example.KU_2024_hackathon.service.ImageTextService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ImageTextController {

    private final ImageTextService imageTextService;

    @PostMapping("/create")
    public ResponseEntity<ImageTextResponseDto> createJournal(@RequestBody ImageTextRequestDto req) throws IOException {
        String q1 = req.getQuestion1();
        String a1 = req.getAnswer1();
        String q2 = req.getQuestion2();
        String a2 = req.getAnswer2();
        String q3 = req.getQuestion3();
        String a3 = req.getAnswer3();

        // 감정, text, 이미지 URL을 생성
        ImageTextResponseDto result = imageTextService.createTextWithImages(q1, a1, q2, a2, q3, a3);

        return ResponseEntity.ok(result);
    }

}
