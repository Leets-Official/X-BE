package com.leets.X.domain.image.service;

import com.leets.X.domain.image.dto.request.ImageDto;
import com.leets.X.domain.image.exception.S3UploadException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;
import software.amazon.awssdk.services.s3.model.S3Exception;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ImageUploadService {
    @Value("${aws.s3.bucketName}")
    private String bucketName;
    @Value("${aws.s3.region}")
    private String region;

    private final S3Client s3Client;

    public List<ImageDto> uploadImages(List<MultipartFile> files) throws IOException {

        List<ImageDto> images = new ArrayList<>();

        for (MultipartFile file : files) {
            String originalName = file.getOriginalFilename();
            String fileName = generateFileName(originalName);

            try {
                // PutObjectRequest 생성 및 설정
                PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                        .bucket(bucketName)
                        .key(fileName)
                        .contentType(file.getContentType())
                        .build();

                // S3에 파일 업로드
                PutObjectResponse response = s3Client.putObject(
                        putObjectRequest,
                        RequestBody.fromInputStream(file.getInputStream(), file.getSize())
                );

                // 업로드 성공 여부 확인
                if (response.sdkHttpResponse().isSuccessful()) {
                    // 업로드된 파일의 URL을 ImageDto로 추가
                    images.add(ImageDto.of(originalName, generateFileUrl(fileName)));
                } else {
                    throw new S3UploadException();
                }
            } catch (S3Exception e) {
                throw new S3UploadException();
            }
        }

        return images;
    }
    // S3에 저장된 파일 URL 생성
    private String generateFileUrl(String fileName) {
        return String.format("https://%s.s3.%s.amazonaws.com/%s", bucketName, region, fileName);
    }

    // 파일 이름을 고유하게 생성하는 메서드
    private String generateFileName(String originalFileName) {
        String uuid = UUID.randomUUID().toString();
        return uuid + "_" + originalFileName.replaceAll("\\s+", "_");
    }

}
