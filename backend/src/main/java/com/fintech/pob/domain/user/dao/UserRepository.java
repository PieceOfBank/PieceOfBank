package com.fintech.pob.domain.user.dao;

import com.fintech.pob.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
}

