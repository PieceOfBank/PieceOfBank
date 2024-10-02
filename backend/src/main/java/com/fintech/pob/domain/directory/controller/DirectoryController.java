package com.fintech.pob.domain.directory.controller;

import com.fintech.pob.domain.directory.entity.DirectoryRequestDto;
import com.fintech.pob.domain.directory.repository.DirectoryRepository;
import com.fintech.pob.domain.directory.service.DirectoryService;
import com.fintech.pob.domain.media.service.MediaUploadService;
import com.fintech.pob.global.auth.jwt.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/directory")
@AllArgsConstructor
public class DirectoryController {


    private final DirectoryService directoryService;
    private final MediaUploadService mediaUploadService;

    @PostMapping("/create")
    public ResponseEntity<DirectoryRequestDto> createDirectory(
            @RequestPart("directory") DirectoryRequestDto directoryDTO,
            @RequestPart("file") MultipartFile file,
            HttpSession session
    ) throws IOException {


        String url = mediaUploadService.uploadFile(file);

        String key = (String) session.getAttribute("userKey");
        UUID userKey = UUID.fromString(key);
        directoryDTO.setUserKey(userKey);
        DirectoryRequestDto createdDirectory = directoryService.createDirectory(directoryDTO, userKey, url);
        return ResponseEntity.ok(createdDirectory);
    }



    @GetMapping("/find")
    public ResponseEntity<List<DirectoryRequestDto>> getDirectoryById(HttpSession session) {
      //  UUID userKey = UUID.fromString("58898a6b-0535-48df-a47f-437e61b92c59");

        String key = (String) session.getAttribute("userKey");
        UUID userKey = UUID.fromString(key);

        List< DirectoryRequestDto> directoryDTO = directoryService.getDirectoryById(userKey);

        return ResponseEntity.ok(directoryDTO);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<DirectoryRequestDto> updateDirectory(
            @PathVariable Long id,
            @RequestBody DirectoryRequestDto directoryDTO,
            HttpServletRequest request,HttpSession session
    ) {
        //String userKey = extractUserKeyFromRequest(request);
    //    UUID userKey = UUID.fromString("58898a6b-0535-48df-a47f-437e61b92c59");
        String key = (String) session.getAttribute("userKey");
        UUID userKey = UUID.fromString(key);
        directoryDTO.setUserKey(userKey);

        DirectoryRequestDto updatedDirectory = directoryService.updateDirectory(id, directoryDTO);
        return ResponseEntity.ok(updatedDirectory);
    }


    @DeleteMapping("/deelte/{id}")
    public ResponseEntity<Void> deleteDirectory(@PathVariable Long id) {
        directoryService.deleteDirectory(id);
        return ResponseEntity.noContent().build();
    }
}
