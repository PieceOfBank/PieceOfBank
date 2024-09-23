package com.fintech.pob.domain.media.service;

import com.fintech.pob.domain.media.entity.Media;
import com.fintech.pob.domain.media.repository.MediaRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class MediaServiceImpl implements MediaService{

    private final MediaRepository mediaRepository;

    @Override
    public void createMedia(Media media) {
        System.out.println(media);
          mediaRepository.save(media);
    }

    @Override
    public void findMedia(Media media) {

    }


}
