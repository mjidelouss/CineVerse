package com.cine.verse.service.impl;

import com.cine.verse.domain.AppUser;
import com.cine.verse.repository.AppUserRepository;
import com.cine.verse.service.AppUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AppUserServiceImpl implements AppUserService {

    private AppUserRepository userRepository;

    @Override
    public AppUser getUserById(Long userId) {
        return userRepository.findById(userId).orElse(null);
    }
}
