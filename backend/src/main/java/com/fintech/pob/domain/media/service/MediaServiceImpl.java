package com.fintech.pob.domain.media.service;

import com.fintech.pob.domain.media.entity.Media;
import com.fintech.pob.domain.media.repository.MediaRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class MediaServiceImpl implements MediaService{

    private final MediaRepository mediaRepository;

    @Override
    public void createMedia(Media media) {
          mediaRepository.save(media);
    }

    @Override
    public Optional<Media> findMedia(Long number) {


        Optional<Media> find = mediaRepository.findByTransactionUniqueNo(number);

        System.out.println(find);
        return find;
    }




}
