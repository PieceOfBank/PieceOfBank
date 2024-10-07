package com.fintech.pob.domain.media.controller;

import com.fintech.pob.domain.media.entity.Media;
import com.fintech.pob.domain.media.entity.MediaDto;
import com.fintech.pob.domain.media.entity.MediaTypeENUM;
import com.fintech.pob.domain.media.service.MediaService;
import com.fintech.pob.domain.media.service.MediaUploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/media")
public class MediaController {

    private final MediaService mediaService;
    private final MediaUploadService mediaUploadService;


    @PostMapping("/upload")
    public ResponseEntity<String> uploadMedia(@RequestParam("file") MultipartFile file,
                                              @RequestParam("transactionUniqueNo") Long transactionUniqueNo,
                                              @RequestParam("type") MediaTypeENUM type,
                                              @RequestParam("content") String content) {
        try {


            String url = mediaUploadService.uploadFile(file);
            System.out.println(url);
            Media media = new Media();
            media.setTransactionUniqueNo(transactionUniqueNo);
            media.setType(type);

            media.setUrl(url);
            media.setContent(content);


            mediaService.createMedia(media);

            return ResponseEntity.ok("미디어가 성공적으로 업로드되었습니다.");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("파일 업로드에 실패했습니다!.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/find")
    public ResponseEntity<Resource> findMedia(
                                              @RequestParam("transactionUniqueNo") Long transactionUniqueNo)
    {

        Optional<Media> media = mediaService.findMedia(transactionUniqueNo);

        if (media.isPresent()) {
            try {
                String mediaUrl = media.get().getUrl();
                Path filePath = Paths.get(mediaUrl);



                Resource resource = (Resource) new UrlResource(filePath.toUri());
               if (resource.exists() || resource.isReadable()) {

                    String contentType = Files.probeContentType(filePath);
                    if (contentType == null) {
                        contentType = "application/octet-stream"; // 기본 MIME 타입 설정
                    }

                    return ResponseEntity.ok()
                            .contentType(MediaType.parseMediaType(contentType))
                            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                            .body(resource);

                } else {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
                }

            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
            }
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }


    }





}
