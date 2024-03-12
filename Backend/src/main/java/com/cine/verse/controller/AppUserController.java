package com.cine.verse.controller;

import com.cine.verse.domain.AppUser;
import com.cine.verse.response.ResponseMessage;
import com.cine.verse.service.AppUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
