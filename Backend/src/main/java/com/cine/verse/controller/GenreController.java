package com.cine.verse.controller;

import com.cine.verse.domain.Genre;
import com.cine.verse.response.ResponseMessage;
import com.cine.verse.service.GenreService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/genre")
@RequiredArgsConstructor
public class GenreController {

    private final GenreService genreService;

    @GetMapping("")
    public ResponseEntity getGenres() {
        List<Genre> genres = genreService.getGenres();
        if (genres.isEmpty()) {
            return ResponseMessage.notFound("Genres Not Found");
        } else {
            return ResponseMessage.ok("Success", genres);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity getGenreById(@PathVariable Long id) {
        Genre genre = genreService.getGenreById(id);
        if (genre == null) {
            return ResponseMessage.notFound("Genre Not Found");
        } else {
            return ResponseMessage.ok("Success", genre);
        }
    }
}
