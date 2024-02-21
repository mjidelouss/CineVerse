package com.cine.verse.controller;

import com.cine.verse.Dto.request.LikeRequest;
import com.cine.verse.Mapper.LikeMapper;
import com.cine.verse.domain.Like;
import com.cine.verse.response.ResponseMessage;
import com.cine.verse.service.LikeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/like")
@RequiredArgsConstructor
public class LikeController {

    private final LikeService likeService;
    private final LikeMapper likeMapper;

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

    @PostMapping("")
    public ResponseEntity addLike(@RequestBody @Valid LikeRequest likeRequest) {
        Like like = likeMapper.likeRequestToLike(likeRequest);
        Like like1 = likeService.addLike(like);
        if(like1 == null) {
            return ResponseMessage.badRequest("Failed To Create Like");
        } else {
            return ResponseMessage.created("Like Created Successfully", like1);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity updateLike(@RequestBody @Valid LikeRequest likeRequest, @PathVariable Long id) {
        Like like = likeMapper.likeRequestToLike(likeRequest);
        Like like1 = likeService.updateLike(like, id);
        if (like1 == null) {
            return ResponseMessage.badRequest("Like Not Updated");
        } else {
            return ResponseMessage.created("Like Updated Successfully", like1);
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity deleteLike(@PathVariable Long id) {
        Like like = likeService.getLikeById(id);
        if (like == null) {
            return ResponseMessage.notFound("Like Not Found");
        } else {
            likeService.deleteLike(id);
            return ResponseMessage.ok("Like Deleted Successfully", like);
        }
    }
}
