package com.cine.verse.service;

import com.cine.verse.domain.AppUser;
import com.cine.verse.domain.Movie;
import com.cine.verse.enums.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AppUserService {

    AppUser getUserById(Long userId);

    Page<AppUser> getUsers(Pageable pageable);

    AppUser saveUser(AppUser user);
    long getTotalUsersByRole(Role role);

    void deleteUser(Long id);
}
