package com.cine.verse.controller;

import com.cine.verse.Dto.request.UpdateProfileDTO;
import com.cine.verse.Dto.response.UserResponse;
import com.cine.verse.domain.AppUser;
import com.cine.verse.enums.Role;
import com.cine.verse.mappers.UserMapper;
import com.cine.verse.response.ResponseMessage;
import com.cine.verse.service.AppUserService;
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

    @GetMapping("/{id}")
    public ResponseEntity getUserById(@PathVariable Long id) {
        AppUser user = appUserService.getUserById(id);
        if (user == null) {
            return ResponseMessage.notFound("User Not Found");
        } else {
            UserResponse userResponse = UserMapper.convertAppUserToUserResponse(user);
            return ResponseMessage.ok("Successfully Brought User", userResponse);
        }
    }

    @GetMapping("/all")
    public ResponseEntity getUsers(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size) {
        Page<AppUser> usersPage = appUserService.getUsers(PageRequest.of(page, size));
        if (usersPage.isEmpty()) {
            return ResponseMessage.notFound("Users Not Found");
        } else {
            Page<UserResponse> userResponsesPage = usersPage.map(UserMapper::convertAppUserToUserResponse);
            return ResponseMessage.ok("Success", userResponsesPage);
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
        AppUser savedUser = appUserService.updateUser(userId, updateProfileDTO);
        if (savedUser == null) {
             return ResponseMessage.notFound("Update User Failed Found");
        } else {
            UserResponse userResponse = UserMapper.convertAppUserToUserResponse(savedUser);
            return ResponseMessage.ok("Profile updated successfully", userResponse);
        }
    }
}
