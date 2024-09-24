package com.fintech.pob.domain.media.controller;

import com.fintech.pob.domain.media.entity.Media;
import com.fintech.pob.domain.media.entity.MediaDto;
import com.fintech.pob.domain.media.entity.MediaTypeENUM;
import com.fintech.pob.domain.media.service.MediaService;
import com.fintech.pob.domain.media.service.MediaUploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

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
    @G





}
