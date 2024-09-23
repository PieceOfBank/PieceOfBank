package com.fintech.pob.domain.media.service;


import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class MediaUploadService {

    private final String uploadDir = "src/main/resources/static/upload"; // 파일 저장 경로 설정

    public String uploadFile(MultipartFile file) throws IOException {
        String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();



        Path filePath = Paths.get(uploadDir, fileName);
        String contentType = Files.probeContentType(filePath);
        if (contentType == null || !contentType.startsWith("image")) {
            throw new IllegalArgumentException("이미지 파일만 업로드할 수 있습니다.");
        }

        Files.createDirectories(filePath.getParent());

        System.out.println("!");
        Files.write(filePath, file.getBytes());

        System.out.println("#");
        return "/upload/" + fileName;

    }
}
