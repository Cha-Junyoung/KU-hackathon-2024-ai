package com.example.KU_2024_hackathon.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

@Service
@Slf4j
@RequiredArgsConstructor
public class S3Service {

    private final S3Client s3Client;

    @Value("${cloud.aws.s3.bucket-name}")
    private String bucketName;

    public String uploadText(String text, String filename) {
        byte[] textBytes = text.getBytes(StandardCharsets.UTF_8); // UTF-8 인코딩 적용

        InputStream inputStream = new ByteArrayInputStream(textBytes);

        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(filename)
                .contentType("text/plain;charset=utf-8")
                .build();

        log.info(inputStream.toString());
        s3Client.putObject(putObjectRequest, RequestBody.fromInputStream(inputStream, textBytes.length));

        return generateFileUrl(filename);
    }

    public String uploadImage(byte[] imageData, String filename) {
        InputStream inputStream = new ByteArrayInputStream(imageData);

        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(filename)
                .contentType("image/png")
                .build();

        s3Client.putObject(putObjectRequest, RequestBody.fromInputStream(inputStream, imageData.length));
        return generateFileUrl(filename);
    }

    private String generateFileUrl(String filename) {
        return String.format("https://%s.s3.amazonaws.com/%s", bucketName, filename);
    }
}
