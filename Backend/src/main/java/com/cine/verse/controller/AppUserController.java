package com.cine.verse.controller;

import com.cine.verse.Dto.request.UpdateProfileDTO;
import com.cine.verse.domain.AppUser;
import com.cine.verse.domain.Movie;
import com.cine.verse.domain.Review;
import com.cine.verse.enums.Role;
import com.cine.verse.response.ResponseMessage;
import com.cine.verse.service.AppUserService;
import com.cine.verse.service.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class AppUserController {

    private final  AppUserService appUserService;
    private final S3Service s3Service;

    @GetMapping("/{id}")
    public ResponseEntity getUserById(@PathVariable Long id) {
        AppUser user = appUserService.getUserById(id);
        if (user == null) {
            return ResponseMessage.notFound("User Not Found");
        } else {
            return ResponseMessage.ok("Successfully Brought User", user);
        }
    }

    @GetMapping("/all")
    public ResponseEntity getUsers(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size) {
        Page<AppUser> usersPage = appUserService.getUsers(PageRequest.of(page, size));
        if (usersPage.isEmpty()) {
            return ResponseMessage.notFound("Users Not Found");
        } else {
            return ResponseMessage.ok("Success", usersPage.getContent());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteUser(@PathVariable Long id) {
        AppUser user = appUserService.getUserById(id);
        if (user == null) {
            return ResponseMessage.notFound("Review Not Found");
        } else {
            appUserService.deleteUser(id);
            return ResponseMessage.ok("Review Deleted Successfully", user);
        }
    }

    @GetMapping("/count")
    public ResponseEntity getTotalUsersByRole() {
        long totalUsers = appUserService.getTotalUsersByRole(Role.USER);
        return ResponseMessage.ok("Success", totalUsers);
    }

    @PutMapping("/profile/{userId}")
    public ResponseEntity updateUserProfile(@PathVariable Long userId, @ModelAttribute UpdateProfileDTO updateProfileDTO) {
        // Retrieve the existing user from the database
        AppUser existingUser = appUserService.getUserById(userId);

        // Check if the user exists
        if (existingUser == null) {
            return ResponseMessage.notFound("Users Not Found");
        }
        // Update the user's profile with the new data
        existingUser.setFirstname(updateProfileDTO.getFirstname());
        existingUser.setLastname(updateProfileDTO.getLastname());
        existingUser.setEmail(updateProfileDTO.getEmail());
        existingUser.setBio(updateProfileDTO.getBio());
        existingUser.setLocation(updateProfileDTO.getLocation());
        if (updateProfileDTO.getImage() != null) {
            existingUser.setImage(s3Service.uploadFile("CineVerse", updateProfileDTO.getImage()));
        }

        // Save the updated user to the database
        AppUser savedUser = appUserService.saveUser(existingUser);
        if (savedUser == null) {
             return ResponseMessage.notFound("Update User Failed Found");
        } else {
            return ResponseMessage.ok("Profile updated successfully", savedUser);
        }
    }
}
