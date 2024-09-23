package com.fintech.pob.domain.media.service;

import com.fintech.pob.domain.media.entity.Media;

public interface MediaService {
    void createMedia(Media media);
    void findMedia(Media media);
}
