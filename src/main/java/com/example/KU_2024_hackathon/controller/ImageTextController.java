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
        if (q1.contains("\n")) q1 = q1.replace("\n", " ");
        String a1 = req.getAnswer1();
        if (a1.contains("\n")) a1 = a1.replace("\n", " ");
        String q2 = req.getQuestion2();
        
        if (q2.contains("\n")) q2 = q2.replace("\n", " ");
        String a2 = req.getAnswer2();
        if (a2.contains("\n")) a2 = a2.replace("\n", " ");

        String q3 = req.getQuestion3();
        if (q3.contains("\n")) q3 = q3.replace("\n", " ");
        String a3 = req.getAnswer3();
        if (a3.contains("\n")) a3 = a3.replace("\n", " ");


        // 감정, text, 이미지 URL을 생성
        ImageTextResponseDto result = imageTextService.createTextWithImages(q1, a1, q2, a2, q3, a3);

        return ResponseEntity.ok(result);
    }

}
