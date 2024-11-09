package com.example.KU_2024_hackathon.service;

import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.InputStream;
import java.net.URL;
import java.net.HttpURLConnection;
import java.io.ByteArrayOutputStream;

@Service
@Slf4j
public class OpenAIService {
    private static final String TEXT_API_URL = "https://api.openai.com/v1/chat/completions";
    private static final String IMAGE_API_URL = "https://api.openai.com/v1/images/generations";

    @Value("${openai.api-key}")
    private String API_KEY;

    public byte[] generateImage(String prompt) {
        log.info("Generating image start with DALL·E 3");
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + API_KEY);

        String requestJson = "{" +
                "\"model\":\"dall-e-3\"," +
                "\"prompt\":\"" + prompt + "\"," +
                "\"n\":1," +
                "\"size\":\"1024x1024\"" +
                "}";

        HttpEntity<String> entity = new HttpEntity<>(requestJson, headers);
        ResponseEntity<String> response = restTemplate.exchange(IMAGE_API_URL, HttpMethod.POST, entity, String.class);

        // JSON 파싱하여 URL 추출
        JSONObject jsonResponse = new JSONObject(response.getBody());
        JSONArray dataArray = jsonResponse.getJSONArray("data");
        String imageUrl = dataArray.getJSONObject(0).getString("url");

        // URL에서 이미지 데이터 가져오기
        try {
            URL url = new URL(imageUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            InputStream inputStream = connection.getInputStream();
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                byteArrayOutputStream.write(buffer, 0, bytesRead);
            }
            return byteArrayOutputStream.toByteArray();
        } catch (Exception e) {
            log.error("Error fetching image data: {}", e.getMessage());
            return null;
        }
    }

    public String generateText(String prompt) {
        log.info("Generating text start with GPT-4o");
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + API_KEY);

        String requestJson = "{" +
                "\"model\":\"gpt-4o\"," +
                "\"messages\":[{\"role\":\"user\",\"content\":\"" + prompt + "\"}]," +
                "\"max_tokens\":1000" +
                "}";

        HttpEntity<String> entity = new HttpEntity<>(requestJson, headers);
        ResponseEntity<String> response = restTemplate.exchange(TEXT_API_URL, HttpMethod.POST, entity, String.class);

        // JSON 파싱하여 content만 추출
        JSONObject jsonResponse = new JSONObject(response.getBody());
        String content = jsonResponse.getJSONArray("choices").getJSONObject(0).getJSONObject("message").getString("content");

        return content;
    }

    public String analyzeEmotion(String prompt) {
        log.info("Generating emotion start with GPT-4o");
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + API_KEY);

        String requestJson = "{" +
                "\"model\":\"gpt-4o\"," +
                "\"messages\":[{\"role\":\"user\",\"content\":\"" + prompt + "\"}]," +
                "\"max_tokens\":50" +
                "}";

        HttpEntity<String> entity = new HttpEntity<>(requestJson, headers);
        ResponseEntity<String> response = restTemplate.exchange(TEXT_API_URL, HttpMethod.POST, entity, String.class);

        log.info("Response: {}", response);

        // JSON 파싱하여 content만 추출
        JSONObject jsonResponse = new JSONObject(response.getBody());
        String content = jsonResponse.getJSONArray("choices").getJSONObject(0).getJSONObject("message").getString("content");

        content = content.replaceAll("\n", "\\n");

        String emotion = switch (content) {
            case String c when c.contains("기쁨") -> "JOY";
            case String c when c.contains("화남") -> "ANGRY";
            case String c when c.contains("슬픔") -> "SAD";
            case String c when c.contains("두려움") -> "AFRAID";
            case String c when c.contains("감탄") -> "ADMIRATION";
            case String c when c.contains("놀람") -> "SURPRISE";
            case String c when c.contains("호기심") -> "INTEREST";
            case String c when c.contains("따분함") -> "BORING";
            default -> "UNKNOWN";
        };

        return emotion;
    }
}
