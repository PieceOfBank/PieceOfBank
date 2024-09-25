package com.fintech.pob.domain.directory.service;


import com.fintech.pob.domain.directory.entity.DirectoryRequestDto;

import java.util.List;

public interface DirectoryService {

    public DirectoryRequestDto createDirectory(DirectoryRequestDto directoryDTO);
    public List<DirectoryRequestDto> getDirectoryById(String userKey);
    public DirectoryRequestDto updateDirectory(Long id,DirectoryRequestDto directoryRequestDto);
    public void deleteDirectory(Long id);
}
