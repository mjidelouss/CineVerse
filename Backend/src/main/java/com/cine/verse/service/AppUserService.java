package com.cine.verse.service;

import com.cine.verse.domain.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AppUserService {

    AppUser getUserById(Long userId);
}
