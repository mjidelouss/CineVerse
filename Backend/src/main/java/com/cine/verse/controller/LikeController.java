package com.cine.verse.controller;

import com.cine.verse.domain.Like;
import com.cine.verse.response.ResponseMessage;
import com.cine.verse.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/like")
@RequiredArgsConstructor
public class LikeController {

    private final LikeService likeService;

    @GetMapping("/all")
    public ResponseEntity getLikes() {
        List<Like> likes = likeService.getLikes();
        if (likes.isEmpty()) {
            return ResponseMessage.notFound("Likes Not Found");
        } else {
            return ResponseMessage.ok("Success", likes);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity getLikeById(@PathVariable Long id) {
        Like like = likeService.getLikeById(id);
        if (like == null) {
            return ResponseMessage.notFound("Like Not Found");
        } else {
            return ResponseMessage.ok("Success", like);
        }
    }

    @PostMapping("/{userId}/{reviewId}")
    public ResponseEntity addLike(@PathVariable Long userId, @PathVariable Long reviewId) {
        Like like = likeService.addLike(userId, reviewId);
        if(like == null) {
            return ResponseMessage.badRequest("Failed To Create Like");
        } else {
            return ResponseMessage.created("Like Created Successfully", like);
        }
    }
}
