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

    private final String uploadDir = "/app/uploads"; // 파일 저장 경로 설정

    public String uploadFile(MultipartFile file) throws IOException {
        //String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();

        String fileName = file.getOriginalFilename();
        System.out.println("filename= "+fileName);

        Path filePath = Paths.get(uploadDir, fileName);
        System.out.println("file path= "+filePath);
        String contentType = Files.probeContentType(filePath);
        if (contentType == null || !contentType.startsWith("image")) {
            throw new IllegalArgumentException("이미지 파일만 업로드할 수 있습니다.");
        }
        Files.createDirectories(filePath.getParent());
        try {
            Files.write(filePath, file.getBytes());
        } catch (IOException e) {

            System.err.println("파일 저장 중 오류가 발생했습니다: " + e.getMessage());
            throw new RuntimeException("파일 저장에 실패했습니다", e);
        }



        

        return uploadDir+"/" + fileName;

    }
}
