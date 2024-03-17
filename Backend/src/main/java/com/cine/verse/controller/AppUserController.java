package com.cine.verse.controller;

import com.cine.verse.domain.AppUser;
import com.cine.verse.domain.Movie;
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
}
