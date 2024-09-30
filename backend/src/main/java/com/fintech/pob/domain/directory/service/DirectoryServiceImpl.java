package com.fintech.pob.domain.directory.service;

import com.fintech.pob.domain.directory.entity.Directory;
import com.fintech.pob.domain.directory.entity.DirectoryRequestDto;
import com.fintech.pob.domain.directory.repository.DirectoryRepository;
import com.fintech.pob.domain.user.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@AllArgsConstructor
public class DirectoryServiceImpl implements  DirectoryService{


    private final DirectoryRepository directoryRepository;
    private final UserRepository userRepository;
    @Override
    public DirectoryRequestDto createDirectory(DirectoryRequestDto directoryDTO,UUID userKey) {

        Directory directory = new Directory();
        directory.setAccountNo(directoryDTO.getAccountNo());
        directory.setInstitutionCode(directoryDTO.getInstitutionCode());
        directory.setName(directoryDTO.getName());
        directory.setUser(userRepository.findByUserKey(userKey).orElse(null));

        Directory savedDirectory = directoryRepository.save(directory);
        return directoryDTO;
    }

    @Override
    public List<DirectoryRequestDto> getDirectoryById(UUID userKey) {
        List<Directory> directories = directoryRepository.findByUser_UserKey(userKey);
        List<DirectoryRequestDto> directoryDtos = new ArrayList<>();
        for (Directory directory : directories) {
            DirectoryRequestDto dto = new DirectoryRequestDto();


          //  dto.setDirectoryId(directory.getDirectoryId());
            dto.setAccountNo(directory.getAccountNo());
            dto.setInstitutionCode(directory.getInstitutionCode());
            dto.setName(directory.getName());


            directoryDtos.add(dto);
        }
        return directoryDtos;
    }

    @Override
    public DirectoryRequestDto updateDirectory(Long id,DirectoryRequestDto directoryRequestDto) {
        return null;
    }

    @Override
    public void deleteDirectory(Long id) {

    }
}
