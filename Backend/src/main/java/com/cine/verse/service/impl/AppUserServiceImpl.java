package com.cine.verse.service.impl;

import com.cine.verse.domain.AppUser;
import com.cine.verse.domain.Movie;
import com.cine.verse.enums.Role;
import com.cine.verse.repository.AppUserRepository;
import com.cine.verse.service.AppUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AppUserServiceImpl implements AppUserService {

    private final AppUserRepository userRepository;

    @Override
    public AppUser getUserById(Long userId) {
        return userRepository.findById(userId).orElse(null);
    }

    @Override
    public Page<AppUser> getUsers(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    @Override
    public AppUser saveUser(AppUser user) {
        return userRepository.save(user);
    }

    @Override
    public long getTotalUsersByRole(Role role) {
        return userRepository.countByRole(role);
    }
}
