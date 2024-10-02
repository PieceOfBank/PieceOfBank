package com.fintech.pob.domain.directory.controller;

import com.fintech.pob.domain.directory.entity.Directory;
import com.fintech.pob.domain.directory.entity.DirectoryRequestDto;
import com.fintech.pob.domain.directory.repository.DirectoryRepository;
import com.fintech.pob.domain.directory.service.DirectoryService;
import com.fintech.pob.domain.media.entity.Media;
import com.fintech.pob.domain.media.service.MediaService;
import com.fintech.pob.domain.media.service.MediaUploadService;
import com.fintech.pob.global.auth.jwt.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.core.io.Resource;
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
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/directory")
@AllArgsConstructor
public class DirectoryController {


    private final DirectoryService directoryService;
    private final MediaUploadService mediaUploadService;
    private final MediaService mediaService;
    private final DirectoryRepository directoryRepository;

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

    @GetMapping("/findUserImage")
    public ResponseEntity<Resource> findMedia(
           String accountNo)
    {

        Optional<Directory> media = directoryRepository.findByAccountNo(accountNo);

        if (media.isPresent()) {
            try {
                String mediaUrl = media.get().getUrl();
                System.out.println(mediaUrl);
                Path filePath = Paths.get(mediaUrl);



                Resource        resource = (Resource) new UrlResource(filePath.toUri());
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
