package com.cine.verse.service;

import com.cine.verse.domain.AppUser;
import com.cine.verse.domain.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AppUserService {

    AppUser getUserById(Long userId);

    Page<AppUser> getUsers(Pageable pageable);
}
