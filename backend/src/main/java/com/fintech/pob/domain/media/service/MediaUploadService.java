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

    private final String uploadDir = "/home/ubuntu/uploads"; // 파일 저장 경로 설정

    public String uploadFile(MultipartFile file) throws IOException {
        String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();


        System.out.println("filename= "+fileName);

        Path filePath = Paths.get(uploadDir, fileName);
        System.out.println("file path= "+filePath);
        String contentType = Files.probeContentType(filePath);
        if (contentType == null || !contentType.startsWith("image")) {
            throw new IllegalArgumentException("이미지 파일만 업로드할 수 있습니다.");
        }

         Files.createDirectories(filePath.getParent());
        Files.write(filePath, file.getBytes());
        return "/home/ubuntu/uploads" + fileName;

    }
}
