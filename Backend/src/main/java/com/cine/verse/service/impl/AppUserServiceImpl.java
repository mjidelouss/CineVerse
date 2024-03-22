package com.cine.verse.service.impl;

import com.cine.verse.Dto.request.UpdateProfileDTO;
import com.cine.verse.domain.AppUser;
import com.cine.verse.domain.Movie;
import com.cine.verse.enums.Role;
import com.cine.verse.repository.AppUserRepository;
import com.cine.verse.response.ResponseMessage;
import com.cine.verse.service.AppUserService;
import com.cine.verse.service.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AppUserServiceImpl implements AppUserService {

    private final AppUserRepository userRepository;
    private final S3Service s3Service;

    @Override
    public AppUser getUserById(Long userId) {
        if (userId == null) {
            throw new IllegalArgumentException("userId cannot be null");
        }
        return userRepository.findById(userId).orElse(null);
    }

    @Override
    public Page<AppUser> getUsers(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    @Override
    public AppUser saveUser(AppUser user) {
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null");
        }
        return userRepository.save(user);
    }

    @Override
    public AppUser updateUser(Long userId, UpdateProfileDTO updateProfileDTO) {
        if (userId == null) {
            throw new IllegalArgumentException("userId cannot be null");
        }
        if (updateProfileDTO == null) {
            throw new IllegalArgumentException("updateProfileDTO cannot be null");
        }
        AppUser existingUser = getUserById(userId);
        if (existingUser == null) {
            return null;
        }
        existingUser.setFirstname(updateProfileDTO.getFirstname());
        existingUser.setLastname(updateProfileDTO.getLastname());
        existingUser.setEmail(updateProfileDTO.getEmail());
        existingUser.setBio(updateProfileDTO.getBio());
        existingUser.setLocation(updateProfileDTO.getLocation());
        if (updateProfileDTO.getImage() != null) {
            existingUser.setImage(s3Service.uploadFile("CineVerse", updateProfileDTO.getImage()));
        }
        return saveUser(existingUser);
    }

    @Override
    public long getTotalUsersByRole(Role role) {
        if (role == null) {
            throw new IllegalArgumentException("Role cannot be null");
        }
        return userRepository.countByRole(role);
    }

    @Override
    public void deleteUser(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("id cannot be null");
        }
        userRepository.deleteById(id);
    }
}
