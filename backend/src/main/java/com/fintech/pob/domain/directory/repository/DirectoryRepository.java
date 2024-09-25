package com.fintech.pob.domain.directory.repository;

import com.fintech.pob.domain.directory.entity.Directory;
import com.fintech.pob.domain.media.entity.Media;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DirectoryRepository  extends JpaRepository<Directory,Long> {
    List<Directory> findByUser_UserKey(String userKey);

}
