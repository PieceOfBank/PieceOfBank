package com.fintech.pob.domain.directory.controller;

import com.fintech.pob.domain.directory.entity.DirectoryRequestDto;
import com.fintech.pob.domain.directory.repository.DirectoryRepository;
import com.fintech.pob.domain.directory.service.DirectoryService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/directory")
@AllArgsConstructor
public class DirectoryController {


    private final DirectoryService directoryService;


    @PostMapping("/create")
    public ResponseEntity<DirectoryRequestDto> createDirectory(
            @RequestBody DirectoryRequestDto directoryDTO,
            HttpServletRequest request // HttpServletRequest를 통해 JWT 추출
    ) {
       // String userKey = extractUserKeyFromRequest(request);
        String userKey="";
        directoryDTO.setUserKey(userKey);
        DirectoryRequestDto createdDirectory = directoryService.createDirectory(directoryDTO);
        return ResponseEntity.ok(createdDirectory);
    }


    @GetMapping("/find")
    public ResponseEntity<DirectoryRequestDto> getDirectoryById(HttpServletRequest request) {
        String userKey="";
        DirectoryRequestDto directoryDTO = directoryService

        return ResponseEntity.ok(directoryDTO);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<DirectoryRequestDto> updateDirectory(
            @PathVariable Long id,
            @RequestBody DirectoryRequestDto directoryDTO,
            HttpServletRequest request
    ) {
        //String userKey = extractUserKeyFromRequest(request);
        String userKey="";
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
