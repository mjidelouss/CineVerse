package com.cine.verse.controller;

import com.cine.verse.domain.Genre;
import com.cine.verse.response.ResponseMessage;
import com.cine.verse.service.GenreService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/")
@RequiredArgsConstructor
public class GenreController {
    private final GenreService genreService;

    @GetMapping("/all")
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

    @PostMapping("")
    public ResponseEntity addGenre(@RequestBody @Valid GenreRequest genreRequest) {
        Genre genre = GenreMapper.mapMemberRequestToMember(genreRequest);
        Genre genre1 = genreService.addGenre(genre);
        if(genre1 == null) {
            return ResponseMessage.badRequest("Failed To Create Genre");
        } else {
            return ResponseMessage.created("Genre Created Successfully", genre1);
        }
    }

    @GetMapping("")
    public ResponseEntity searchGenre(@RequestParam String searchTerm) {
        List<Genre> genres = genreService.searchGenre(searchTerm);
        if (genres.isEmpty()) {
            return ResponseMessage.notFound("Genre Not Found");
        } else {
            return ResponseMessage.ok("Success", genres);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity updateGenre(@RequestBody @Valid GenreRequest genreRequest, @PathVariable Long id) {
        Genre genre = GenreMapper.mapMemberRequestToMember(genreRequest);
        Genre genre1 = genreService.updateGenre(genre, id);
        if (genre1 == null) {
            return ResponseMessage.badRequest("Genre Not Updated");
        } else {
            return ResponseMessage.created("Genre Updated Successfully", genre1);
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity deleteGenre(@PathVariable Long id) {
        Genre genre = genreService.getGenreById(id);
        if (genre == null) {
            return ResponseMessage.notFound("Genre Not Found");
        } else {
            genreService.deleteGenre(id);
            return ResponseMessage.ok("Genre Deleted Successfully", genre);
        }
    }
}
